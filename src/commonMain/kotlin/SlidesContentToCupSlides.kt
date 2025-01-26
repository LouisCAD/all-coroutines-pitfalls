import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dsl.SlidesBuilder
import dsl.builder.SlidesDataBuilder
import dsl.model.*
import net.kodein.cup.Slide
import net.kodein.cup.SlideSpecs


fun buildSlides(
    slidesMaker: CupSlidesMaker = defaultCupSlidesMaker,
    builder: SlidesBuilder.() -> Unit
): List<Slide> {
    return slidesMaker.buildSlides(SlidesDataBuilder().apply(builder).build())
}


val defaultCupSlidesMaker: CupSlidesMaker = DefaultCupSlidesMaker(
    title = { parentTitles, slideTitle ->
        TODO()
    },
    a = {}
)

private class DefaultCupSlidesMaker(
    private val title: @Composable ColumnScope.(
        parent: List<SlideTitle>,
        SlideTitle?
    ) -> Unit,
    a: @Composable (step: Int) -> Unit,
) : CupSlidesMaker {
    override fun buildSlides(data: List<SlideData>): List<Slide> = buildList {
        for (i in 0..data.lastIndex) {
            addSlide(
                index = i,
                data = data[i],
                prev = data.getOrNull(i - 1),
                next = data.getOrNull(i + 1),
            )
        }
    }

    //TODO: When on the last step of a slide, show a hint (e.g. a camera emoji or icon),
    // to let people know it's time to take a picture before the content goes away.

    //TODO: Do a chapter closing
    private fun MutableList<Slide>.addSlide(
        index: Int,
        data: SlideData,
        prev: SlideData?,
        next: SlideData?
    ) {
        val stepCount: Int = when (data) {
            is SlideData.Comparison -> data.slides.sumOf { it.content.stepsCount() }
            is SlideData.TitleAndContent -> data.content.stepsCount()
        }
        this += when (data) {
            is SlideData.Comparison -> Slide(
                name = data.slideName(index),
                stepCount = stepCount,
                specs = SlideSpecs()
            ) { step ->
                Column(Modifier.fillMaxSize()) {
                    title(data.parentTitles, data.currentTitle)
                    Row(Modifier.fillMaxWidth().weight(1f)) {
                        data.slides.forEachIndexed { index, subSlide ->
                            //TODO: Map step depending on the delivery strategy.
                            TODO()
                        }
                    }
                }
            }
            is SlideData.Single -> Slide(
                name = data.slideName(index),
                stepCount = stepCount,
                specs = SlideSpecs()
            ) { step ->
                TODO()
            }
            is SlideData.SubSlide -> Slide(
                name = data.slideName(index),
                stepCount = stepCount,
                specs = SlideSpecs()
            ) { step ->
                TODO()
            }
        }
    }

}

private fun SlideData.slideName(index: Int): String = currentTitle?.smallTitle ?: index.toString()

private fun SlideContent.stepsCount(): Int = when (this) {
    is SlideContent.Elements -> this.elements.sumOf { it.countSteps() }
    is SlideContent.SingleElement -> 0
}

private fun Tree<SlideContentItem>.countSteps(): Int {
    return 1 + (if (data.sideLabel != null) 1 else 0) + nodes.sumOf { it.countSteps() }
}
