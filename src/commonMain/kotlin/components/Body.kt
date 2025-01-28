package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dsl.TextContentKind
import dsl.model.SlideContent
import dsl.model.SlideTitle

@Composable
fun Body(
    modifier: Modifier,
    parentTitles: List<SlideTitle>?,
    currentTitle: SlideTitle?,
    content: SlideContent,
    step: Int
) {
    when (content) {
        is SlideContent.Elements -> Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
