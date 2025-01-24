import dsl.SlidesBuilder
import dsl.builder.SlidesDataBuilder
import net.kodein.cup.Slide

//TODO: When on the last step of a slide, show a hint (e.g. a camera emoji or icon),
// to let people know it's time to take a picture before the content goes away.

//TODO: Do a chapter closing

fun buildSlides(
    slidesMaker: CupSlidesMaker,
    builder: SlidesBuilder.() -> Unit
): List<Slide> {
    return slidesMaker.buildSlides(SlidesDataBuilder().apply(builder).build())
}
