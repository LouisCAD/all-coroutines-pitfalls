package dsl.builder

import dsl.Disposition
import dsl.SlideBuilder
import dsl.SlidesBuilder
import dsl.TextContentKind
import dsl.model.SlideData

class SlidesDataBuilder : SlidesBuilder() {

    fun build(): List<SlideData> = tree.toList()

    private val tree = mutableListOf<SlideData>()
    override fun slide(title: String, contentKind: TextContentKind, subtitle: String?) {
        TODO()
    }

    override fun slide(
        title: String?,
        disposition: Disposition,
        startCentered: Boolean,
        block: SlideBuilder.() -> Unit
    ) {
        TODO()
    }

    override fun slidesGroup(
        title: String,
        disposition: Disposition,
        smallTitle: String?,
        subtitle: String?,
        slideContent: SlideBuilder.() -> Unit,
        groupContent: SlidesBuilder.() -> Unit
    ) {
        TODO()
    }

    override fun comparison(block: SlidesBuilder.() -> Unit) {
        TODO()
    }
}
