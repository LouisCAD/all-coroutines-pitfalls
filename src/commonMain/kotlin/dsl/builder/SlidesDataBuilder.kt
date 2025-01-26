package dsl.builder

import dsl.Disposition
import dsl.SlideBuilder
import dsl.SlidesBuilder
import dsl.TextContentKind
import dsl.model.*

class SlidesDataBuilder private constructor(
    private val parentTitles: List<SlideTitle>,
    private val slideDataList: MutableList<SlideData>,
    private val onNewTitle: (newTitle: String) -> Unit
): SlidesBuilder() {

    constructor() : this(emptyList(), mutableListOf(), {})

    fun build(): List<SlideData> = slideDataList.toList()

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
            currentTitle = title?.let {
                SlideTitle(
                    text = it,
                    smallTitle = null,
                    subtitle = subtitle
                )
            },
            content = SlideContent.Elements(
                disposition = disposition,
                elements = mutableListOf<MutableTree<SlideContentItem>>().also { list ->
                    SlideContentItemBuilder(list).block()
                }
            )
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
        val slideTitle = SlideTitle(
            text = title,
            smallTitle = smallTitle,
            subtitle = subtitle
        )
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
        disposition: Disposition?,
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
        val elements: MutableList<Tree<SlideContentItem>>?
        slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = slideTitle,
            content = when (disposition) {
                null -> {
                    elements = null
                    null
                }
                else -> {
                    elements = mutableListOf()
                    SlideContent.Elements(
                        disposition = disposition,
                        elements = elements
                    )
                }
            } ?: SlideContent.SingleElement(TextContentKind.CenteredTitle)
        )
        SlidesDataBuilder(
            parentTitles = parentTitles + slideTitle,
            slideDataList = slideDataList,
            onNewTitle = { newTitle ->
                elements?.add(createLeaf(SlideContentItem(text = newTitle, sideLabel = null)))
            },
        ).groupContent()
    }

    override fun comparison(
        title: String?,
        subtitle: String?,
        titleOnlyForOverview: Boolean,
        block: SlidesBuilder.() -> Unit
    ) {
        if (title != null) onNewTitle(title)
        val slideTitle = if (titleOnlyForOverview.not()) title?.let {
            SlideTitle(
                text = it,
                smallTitle = null,
                subtitle = subtitle
            )
        } else null
        slideDataList += SlideData.Comparison(
            parentTitles = parentTitles,
            currentTitle = slideTitle,
            slides = TODO("Create a super common interface for slides without group support")
        )
    }

    private fun <T> createLeaf(data: T): Tree<T> = object : Tree<T> {
        override val data: T = data
        override val nodes: List<Tree<T>> get() = emptyList()
    }
}
