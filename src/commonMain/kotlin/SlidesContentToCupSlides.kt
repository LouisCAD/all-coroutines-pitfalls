import dsl.*
import net.kodein.cup.Slide

//TODO: When on the last step of a slide, show a hint (e.g. a camera emoji or icon),
// to let people know it's time to take a picture before the content goes away.

//TODO: Do a chapter closing

fun slides(
    builder: SlidesBuilder.() -> Unit
): List<Slide> {
    return CupSlidesBuilder().apply(builder).toSlides()
}

private class CupSlidesBuilder : SlidesBuilder {
    fun toSlides(): List<Slide> {
        TODO()
    }

    override fun String.slide(whatever: TextContentKind, subtitle: String?) {
        TODO()
    }

    override fun slide(disposition: Disposition, block: SlideBuilder.() -> Unit) {
        TODO()
    }

    override fun String.slide(disposition: Disposition, block: SlideBuilder.() -> Unit) {
        TODO()
    }

    override fun String.slidesGroup(disposition: Disposition, smallTitle: String, block: SlideGroup.() -> Unit) {
        TODO()
    }

    override fun comparison(block: SlidesBuilder.() -> Unit) {
        TODO()
    }
}


