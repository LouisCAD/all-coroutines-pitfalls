package dsl.builder

import dsl.*
import dsl.model.*

class SlidesDataBuilder private constructor(
    private val parentTitles: List<SlideTitle>,
    private val slideDataList: MutableList<SlideData.TopLevel>,
    private val onNewTitle: (newTitle: String) -> Unit
): SlidesBuilder() {

    constructor() : this(emptyList(), mutableListOf(), {})

    fun build(): List<SlideData.TopLevel> = slideDataList.toList()

    override fun slideWithTextContent(
        title: String,
        contentKind: TextContentKind,
        subtitle: String?
    ) {
        onNewTitle(title)
        slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = SlideTitle(
                text = title,
                smallTitle = null,
                subtitle = subtitle
            ),
            content = SlideContent.TitlesOnly(contentKind = contentKind)
        )
    }

    override fun slideWithTextTree(
        title: String?,
        subtitle: String?,
        disposition: Disposition,
        startCentered: Boolean,
        block: SlideBuilder.() -> Unit
    ) {
        if (title != null) onNewTitle(title)
        val currentTitle = slideTitleOrNull(title = title, subtitle = subtitle)
        //TODO: Don't create this slide, and pass startCentered instead, to allow animation.
        if (startCentered) slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = currentTitle,
            content = SlideContent.TitlesOnly(contentKind = TextContentKind.CenteredTitle)
        )
        slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = currentTitle,
            content = slideContentElements(disposition, block)
        )
    }

    override fun slidesGroupWithTextContent(
        title: String,
        contentKind: TextContentKind,
        smallTitle: String?,
        subtitle: String?,
        groupContent: SlidesBuilder.() -> Unit
    ) {
        onNewTitle(title)
        val slideTitle = SlideTitle(text = title, smallTitle = smallTitle, subtitle = subtitle)
        slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = slideTitle,
            content = SlideContent.TitlesOnly(contentKind = contentKind)
        )
        SlidesDataBuilder(
            parentTitles = parentTitles + slideTitle,
            slideDataList = slideDataList,
            onNewTitle = {}
        ).groupContent()
    }

    override fun slidesGroupWithTextTree(
        title: String,
        disposition: Disposition,
        smallTitle: String?,
        subtitle: String?,
        groupContent: SlidesBuilder.() -> Unit
    ) {
        onNewTitle(title)
        val slideTitle = SlideTitle(
            text = title,
            smallTitle = smallTitle,
            subtitle = subtitle
        )
        val elements = mutableListOf<Tree<SlideContentItem>>()
        slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = slideTitle,
            content = SlideContent.Elements(
                disposition = disposition,
                elements = elements
            )
        )
        SlidesDataBuilder(
            parentTitles = parentTitles + slideTitle,
            slideDataList = slideDataList,
            onNewTitle = { newTitle ->
                elements.add(Tree(SlideContentItem(text = newTitle, sideLabel = null, disposition = disposition), emptyList()))
            },
        ).groupContent()
    }

    override fun sideBySide(
        title: String?,
        subtitle: String?,
        overviewTitle: String?,
        delivery: SideBySideDelivery,
        block: SubSlidesBuilder.() -> Unit
    ) {
        if (overviewTitle != null) onNewTitle(overviewTitle)
        slideDataList += SlideData.SideBySide(
            parentTitles = parentTitles,
            currentTitle = slideTitleOrNull(title = title, subtitle = subtitle),
            overviewTitle = overviewTitle,
            delivery = delivery,
            slides = SubSlidesDataBuilder().apply(block).build()
        )
    }
}
