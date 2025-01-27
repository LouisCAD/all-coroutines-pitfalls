import androidx.collection.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dsl.SideBySideDelivery
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
    body = { parentTitles, currentTitle, content, step ->
        TODO()
    }
)

private class DefaultCupSlidesMaker(
    private val title: @Composable ColumnScope.(
        parent: List<SlideTitle>,
        SlideTitle?
    ) -> Unit,
    private val body: @Composable (
        parentTitles: List<SlideTitle>?,
        currentTitle: SlideTitle?,
        content: SlideContent,
        step: Int
    ) -> Unit,
) : CupSlidesMaker {
    override fun buildSlides(data: List<SlideData.TopLevel>): List<Slide> = buildList {
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
        data: SlideData.TopLevel,
        prev: SlideData?,
        next: SlideData?
    ) {
        this += when (data) {
            is SlideData.SideBySide -> {
                val stepRetriever = SideBySideStepRetriever(data)
                Slide(
                    name = data.slideName(index),
                    stepCount = stepRetriever.stepCount,
                    specs = SlideSpecs()
                ) { globalStep ->
                    Column(Modifier.fillMaxSize()) {
                        title(data.parentTitles, data.currentTitle)
                        Row(Modifier.fillMaxWidth().weight(1f)) {
                            data.slides.forEachIndexed { index, subSlide ->
                                val step = stepRetriever.indexForColumn(
                                    delivery = data.delivery,
                                    columnIndex = index,
                                    step = globalStep
                                )
                                body(null, subSlide.currentTitle, subSlide.content, step)
                            }
                        }
                    }
                }
            }
            is SlideData.Single -> Slide(
                name = data.slideName(index),
                stepCount = data.content.stepsCount(),
                specs = SlideSpecs()
            ) { step ->
                body(data.parentTitles, data.currentTitle, data.content, step)
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

private class SideBySideStepRetriever(
    private val data: SlideData.SideBySide,
) {
    fun indexForColumn(
        delivery: SideBySideDelivery,
        columnIndex: Int,
        step: Int
    ): Int = when (delivery) {
        SideBySideDelivery.PageByPage -> step - stepCounts.take(columnIndex).sum()
        SideBySideDelivery.PerLine -> columnLocalSteps[columnIndex][step]
    }

    private val stepCounts: List<Int> = data.slides.map { it.content.stepsCount() }
    val stepCount = stepCounts.sum()
    private val columnLocalSteps: List<IntArray> = data.slides.map { IntArray(stepCount) { -1 } }
    private val columnLocalSteps2: List<MutableIntList> = data.slides.map { MutableIntList(initialCapacity = stepCount) }

    init {
        var lineIndex = 0
        var targetColumnIndex = 0
        val columnCount = data.slides.size
        for (globalStep in 0 until stepCount) {
            run updateTargetColumn@{
                repeat(columnCount) {
                    val hasLineForCurrentStep = lineIndex < stepCounts[targetColumnIndex]
                    if (hasLineForCurrentStep) {
                        return@updateTargetColumn
                    } else {
                        if (targetColumnIndex + 1 == columnCount) {
                            targetColumnIndex = 0
                            lineIndex++
                        } else targetColumnIndex++
                    }
                }
                error("WTF? We should have found a column")
            }
            for (columnIndex in 0..<columnCount) {
                val localSteps = columnLocalSteps2[columnIndex]
                if (columnIndex == targetColumnIndex) {
                    localSteps[globalStep] = lineIndex
                } else {
                    val lastLineIndexForColumn = if (localSteps.isEmpty()) -1 else localSteps.last()
                    localSteps[globalStep] = lastLineIndexForColumn
                }
            }
        }
    }
}
