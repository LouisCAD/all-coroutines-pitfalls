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
            content = SlideContent.SingleElement(
                contentKind = contentKind
            )
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
        slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = slideTitleOrNull(title = title, subtitle = subtitle),
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
            content = SlideContent.SingleElement(contentKind = contentKind)
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
                elements.add(Tree(SlideContentItem(text = newTitle, sideLabel = null), emptyList()))
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
        val subSlidesBuilder = SubSlidesDataBuilder()
        slideDataList += SlideData.SideBySide(
            parentTitles = parentTitles,
            currentTitle = slideTitleOrNull(title = title, subtitle = subtitle),
            delivery = delivery,
            slides = subSlidesBuilder.build()
        )
    }
}
