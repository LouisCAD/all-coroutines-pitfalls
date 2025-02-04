package components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import dsl.Disposition
import dsl.model.SlideContentItem
import dsl.model.Tree

@Composable
fun ContentItems(
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
    val modifier = when (disposition) {
        is Disposition.Bullets -> Modifier.padding(start = 16.dp * depth)
        is Disposition.List -> Modifier
    }
    items.fastForEachIndexed { i, tree ->
        val visible = step >= index
        val blurFactor by animateFloatAsState(
            targetValue = if (visible) 0f else 1f,
            animationSpec = unblurAnimation
        )
        val prefix = when (disposition) {
            Disposition.Bullets.Abc -> "${ALPHABET[i]}. "
            Disposition.Bullets -> "• "
            Disposition.Bullets.Numbers -> "${i + 1}. "
            Disposition.Bullets.Arrow -> "→ "
            Disposition.Bullets.Invisible -> "   "
            is Disposition.List -> ""
        }
        val textStyle = when (disposition) {
            is Disposition.Bullets -> MaterialTheme.typography.bodyLarge
            Disposition.List.BigCentered -> MaterialTheme.typography.displayMedium
            is Disposition.List -> when (depth) {
                0 -> MaterialTheme.typography.titleLarge
                1 -> MaterialTheme.typography.titleMedium
                2 -> MaterialTheme.typography.titleSmall
                3 -> MaterialTheme.typography.bodyLarge
                4 -> MaterialTheme.typography.bodyMedium
                5 -> MaterialTheme.typography.bodySmall
                else -> MaterialTheme.typography.bodySmall
            }
        }
        Txt(
            text = prefix + tree.data.text,
            modifier = modifier.blur(
                radius = 12.dp * blurFactor,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            ),
            textAlign = if (disposition is Disposition.List.BigCentered) TextAlign.Center else null,
            style = textStyle
        )
        index++
        index += ContentItems(
            disposition = tree.data.disposition ?: disposition,
            items = tree.nodes,
            depth = depth + 1,
            stepIndexOffset = index,
            step = step
        )
    }
    return index - stepIndexOffset
}

private const val ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ"
