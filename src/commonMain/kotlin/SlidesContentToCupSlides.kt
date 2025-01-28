import androidx.collection.MutableIntList
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import dsl.Disposition
import dsl.SideBySideDelivery
import dsl.SlidesBuilder
import dsl.TextContentKind
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
        Title(parentTitles = parentTitles, slideTitle = slideTitle)
    },
    body = { parentTitles, currentTitle, content, step ->
        Body(
            modifier = Modifier.fillMaxWidth().weight(1f),
            parentTitles = parentTitles,
            currentTitle = currentTitle,
            content = content,
            step = step
        )
    }
)

@Composable
private fun Title(
    parentTitles: List<SlideTitle>,
    slideTitle: SlideTitle?
) = Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    val targetTitle: SlideTitle?
    val targetParentTitles: List<String> =
    if (slideTitle == null) {
        targetTitle = parentTitles.lastOrNull()
        parentTitles.dropLast(1)
    } else {
        targetTitle = slideTitle
        parentTitles
    }.map { it.smallTitle ?: it.text }.filter { it.isNotEmpty() }
    if (targetParentTitles.isNotEmpty()) {
        val text = targetParentTitles.joinToString(separator = " > ")
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
    targetTitle?.let {
        Text(
            text = it.text,
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displayLarge
        )
        it.subtitle?.let { text ->
            Text(
                text = text,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Composable
private fun Body(
    modifier: Modifier,
    parentTitles: List<SlideTitle>?,
    currentTitle: SlideTitle?,
    content: SlideContent,
    step: Int
) {
    when (content) {
        is SlideContent.Elements -> Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ContentItems(
                disposition = content.disposition,
                items = content.elements,
                step = step
            )
        }
        is SlideContent.SingleElement -> Box(modifier) {
            val textStyle = when (content.contentKind) {
                TextContentKind.BigFact -> MaterialTheme.typography.displayLarge
                TextContentKind.CenteredTitle -> MaterialTheme.typography.displayLarge
                TextContentKind.NewSection -> MaterialTheme.typography.displayLarge
                TextContentKind.PresentationOpening -> MaterialTheme.typography.displayLarge
            }
            currentTitle?.let {
                Text(it.text, Modifier.align(Alignment.Center), style = textStyle)
            }
        }
    }
}

@Composable
private fun ContentItems(
    disposition: Disposition,
    items: List<Tree<SlideContentItem>>,
    step: Int
) = ContentItems(
    disposition = disposition,
    items = items,
    depth = 0,
    stepIndexOffset = 0,
    step = step
)

@Composable
private fun ContentItems(
    disposition: Disposition,
    items: List<Tree<SlideContentItem>>,
    depth: Int,
    stepIndexOffset: Int,
    step: Int
): Int {
    var index = stepIndexOffset
    items.forEach { tree ->
        val visible = step >= index
        Text(
            text = tree.data.text,
            modifier = Modifier.alpha(if (visible) 1f else 0.05f).padding(start = 16.dp * depth)
        )
        index++
        index += ContentItems(
            disposition = disposition,
            items = tree.nodes,
            depth = depth + 1,
            stepIndexOffset = index,
            step = step
        )
    }
    return index - stepIndexOffset
}

private class DefaultCupSlidesMaker(
    private val title: @Composable ColumnScope.(
        parent: List<SlideTitle>,
        SlideTitle?
    ) -> Unit,
    private val body: @Composable ColumnScope.(
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

    private val slideRootModifier = Modifier.fillMaxSize().padding(16.dp)
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
                        title(data.parentTitles, data.currentTitle)
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
                                    title(emptyList(), subSlide.currentTitle)
                                    Spacer(Modifier.height(16.dp))
                                    body(null, subSlide.currentTitle, subSlide.content, step)
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
                    if (data.content !is SlideContent.SingleElement) {
                        title(data.parentTitles, data.currentTitle)
                    }
                    Spacer(Modifier.height(16.dp))
                    body(data.parentTitles, data.currentTitle, data.content, step - 1)
                }
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
        globalStep: Int
    ): Int {
        if (globalStep < 0) return -1
        return when (delivery) {
            SideBySideDelivery.PageByPage -> globalStep - stepCounts.take(columnIndex).sum()
            SideBySideDelivery.PerLine -> columnLocalSteps[columnIndex][globalStep]
        }
    }

    private val stepCounts: List<Int> = data.slides.map { it.content.stepsCount() }
    val stepCount = stepCounts.sum()
    private val columnLocalSteps: List<MutableIntList> = data.slides.map { MutableIntList(initialCapacity = stepCount) }

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
                val localSteps = columnLocalSteps[columnIndex]
                localSteps += if (columnIndex == targetColumnIndex) {
                    lineIndex
                } else {
                    val lastLineIndexForColumn = if (localSteps.isEmpty()) -1 else localSteps.last()
                    lastLineIndexForColumn
                }
            }
            if (targetColumnIndex + 1 == columnCount) {
                targetColumnIndex = 0
                lineIndex++
            } else targetColumnIndex++
        }
    }
}
