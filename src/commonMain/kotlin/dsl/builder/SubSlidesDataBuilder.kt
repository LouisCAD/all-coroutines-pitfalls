package dsl.builder

import dsl.Disposition
import dsl.SlideBuilder
import dsl.SubSlidesBuilder
import dsl.TextContentKind
import dsl.model.SlideData

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
        TODO()
    }

    override fun slideWithTextTree(
        title: String?,
        subtitle: String?,
        disposition: Disposition,
        startCentered: Boolean,
        block: SlideBuilder.() -> Unit
    ) {
        TODO()
    }
}
