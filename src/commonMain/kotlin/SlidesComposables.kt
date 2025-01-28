import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import dsl.Disposition
import dsl.SlidesBuilder
import dsl.TextContentKind
import dsl.builder.SlidesDataBuilder
import dsl.model.SlideContent
import dsl.model.SlideContentItem
import dsl.model.SlideTitle
import dsl.model.Tree
import net.kodein.cup.Slide


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

private val unblurAnimation = spring<Float>(stiffness = Spring.StiffnessHigh)

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
        val blurFactor by animateFloatAsState(
            targetValue = if (visible) 0f else 1f,
            animationSpec = unblurAnimation
        )
        Text(
            text = tree.data.text,
            modifier = Modifier.padding(start = 16.dp * depth).blur(
                radius = 12.dp * blurFactor,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
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
