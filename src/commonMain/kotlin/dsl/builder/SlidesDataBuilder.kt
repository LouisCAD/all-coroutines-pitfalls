package dsl.builder

import dsl.Disposition
import dsl.SlideBuilder
import dsl.SlidesBuilder
import dsl.TextContentKind
import dsl.model.*

class SlidesDataBuilder private constructor(
    private val parentTitles: List<SlideTitle>,
    private val slideDataList: MutableList<SlideData>
): SlidesBuilder() {

    constructor() : this(emptyList(), mutableListOf())

    fun build(): List<SlideData> = slideDataList.toList()

    override fun slideWithTextContent(
        title: String,
        contentKind: TextContentKind,
        subtitle: String?
    ) {
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
            slideDataList = slideDataList
        ).groupContent()
    }

    override fun slidesGroupWithTextTree(
        title: String,
        disposition: Disposition?,
        smallTitle: String?,
        subtitle: String?,
        groupContent: SlidesBuilder.() -> Unit
    ) {
        val slideTitle = SlideTitle(
            text = title,
            smallTitle = smallTitle,
            subtitle = subtitle
        )
        val elements = mutableListOf<Tree<SlideContentItem>>()
        slideDataList += SlideData.Single(
            parentTitles = parentTitles,
            currentTitle = slideTitle,
            content = disposition?.let {
                SlideContent.Elements(
                    disposition = it,
                    elements = elements
                )
            } ?: SlideContent.SingleElement(TextContentKind.CenteredTitle)
        )
        SlidesDataBuilder(
            parentTitles = parentTitles + slideTitle,
            slideDataList = slideDataList
        ).groupContent()
    }

    override fun comparison(
        title: String?,
        subtitle: String?,
        block: SlidesBuilder.() -> Unit
    ) {
        val slideTitle = title?.let {
            SlideTitle(
                text = it,
                smallTitle = null,
                subtitle = subtitle
            )
        }
        slideDataList += SlideData.Comparison(
            parentTitles = parentTitles,
            currentTitle = slideTitle,
            slides = TODO()
        )
        TODO()
    }
}
