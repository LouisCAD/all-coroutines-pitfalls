package dsl.builder

import dsl.Disposition
import dsl.SlideBuilder
import dsl.SubSlidesBuilder
import dsl.TextContentKind
import dsl.model.*

class SubSlidesDataBuilder(
    private val slideDataList: MutableList<SlideData.SubSlide>,
) : SubSlidesBuilder() {

    constructor() : this(mutableListOf())

    fun build(): List<SlideData.SubSlide> = slideDataList.toList()

    override fun slideWithTextContent(
        title: String,
        contentKind: TextContentKind,
        subtitle: String?
    ) {
        slideDataList += SlideData.SubSlide(
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
        slideDataList += SlideData.SubSlide(
            currentTitle = slideTitleOrNull(title = title, subtitle = subtitle),
            content = slideContentElements(disposition, block)
        )
    }
}
