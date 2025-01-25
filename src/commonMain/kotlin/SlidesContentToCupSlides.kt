import dsl.SlidesBuilder
import dsl.builder.SlidesDataBuilder
import dsl.model.SlideContent
import dsl.model.SlideContentItem
import dsl.model.SlideData
import dsl.model.Tree
import net.kodein.cup.Slide
import net.kodein.cup.SlideSpecs


fun buildSlides(
    slidesMaker: CupSlidesMaker = defaultCupSlidesMaker,
    builder: SlidesBuilder.() -> Unit
): List<Slide> {
    return slidesMaker.buildSlides(SlidesDataBuilder().apply(builder).build())
}

val defaultCupSlidesMaker: CupSlidesMaker = object : CupSlidesMaker {
    //TODO: When on the last step of a slide, show a hint (e.g. a camera emoji or icon),
    // to let people know it's time to take a picture before the content goes away.

    //TODO: Do a chapter closing
    override fun buildSlides(data: List<SlideData>): List<Slide> = buildList {
        for (i in 0..data.lastIndex) {
            addSlide(
                data = data[i],
                prev = data.getOrNull(i - 1),
                next = data.getOrNull(i + 1),
            )
        }
    }

    private fun MutableList<Slide>.addSlide(data: SlideData, prev: SlideData?, next: SlideData?) {
        val stepCount: Int = when (data) {
            is SlideData.Comparison -> data.slides.sumOf { it.content.stepsCount() }
            is SlideData.TitleAndContent -> data.content.stepsCount()
        }
        this += Slide(
            name = "",
            stepCount = stepCount,
            specs = SlideSpecs()
        ) { step ->
            when (data) {
                is SlideData.Comparison -> TODO()
                is SlideData.Single -> TODO()
                is SlideData.SubSlide -> TODO()
            }
        }
    }

}

private fun SlideContent.stepsCount(): Int = when (this) {
    is SlideContent.Elements -> this.tree.countSteps()
    is SlideContent.SingleElement -> 0
}

private fun Tree<SlideContentItem>.countSteps(): Int {
    return 1 + (if (data.sideLabel != null) 1 else 0) + nodes.sumOf { it.countSteps() }
}
