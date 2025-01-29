import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dsl.model.SlideContent
import dsl.model.SlideData
import dsl.model.SlideTitle
import net.kodein.cup.Slide
import net.kodein.cup.SlideSpecs

class DefaultCupSlidesMaker(
    private val spacing: Dp = 16.dp,
    private val title: @Composable ColumnScope.(
        parent: List<SlideTitle>,
        SlideTitle?,
        content: SlideContent.TitlesOnly?
    ) -> Unit,
    private val body: @Composable ColumnScope.(
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

    private val slideRootModifier = Modifier.fillMaxSize().padding(spacing)
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
                    stepCount = stepRetriever.stepCount + 1,
                    specs = SlideSpecs()
                ) { globalStep ->
                    Column(slideRootModifier) {
                        title(data.parentTitles, data.currentTitle, null)
                        Row(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            data.slides.forEachIndexed { index, subSlide ->
                                val step = stepRetriever.indexForColumn(
                                    delivery = data.delivery,
                                    columnIndex = index,
                                    globalStep = globalStep - 1
                                )
                                Column(Modifier.fillMaxHeight().weight(1f)) {
                                    Content(
                                        parentTitles = emptyList(),
                                        currentTitle = subSlide.currentTitle,
                                        content = subSlide.content,
                                        step = step
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is SlideData.Single -> Slide(
                name = data.slideName(index),
                stepCount = data.content.stepsCount() + 1,
                specs = SlideSpecs()
            ) { step ->
                Column(slideRootModifier) {
                    Content(
                        parentTitles = data.parentTitles,
                        currentTitle = data.currentTitle,
                        content = data.content,
                        step = step - 1
                    )
                }
            }
        }
    }

    @Composable
    private fun ColumnScope.Content(
        parentTitles: List<SlideTitle>,
        currentTitle: SlideTitle?,
        content: SlideContent,
        step: Int
    ) {
        title(parentTitles, currentTitle, content as? SlideContent.TitlesOnly)
        when (content) {
            is SlideContent.TitlesOnly -> Unit
            is SlideContent.Elements -> {
                Spacer(Modifier.height(spacing))
                body(content, step)
            }
        }
    }
}
