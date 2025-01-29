package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dsl.Disposition
import dsl.TextContentKind
import dsl.model.SlideContent

@Composable
fun Body(
    modifier: Modifier,
    content: SlideContent,
    step: Int
) {
    when (content) {
        is SlideContent.Elements -> Column(
            modifier = modifier,
            verticalArrangement = when (content.disposition) {
                Disposition.List.BigCentered -> Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                else -> Arrangement.spacedBy(8.dp)
            },
            horizontalAlignment = when (content.disposition) {
                Disposition.List.BigCentered -> Alignment.CenterHorizontally
                else -> Alignment.Start
            }
        ) {
            ContentItems(
                disposition = content.disposition,
                items = content.elements,
                step = step
            )
        }
        is SlideContent.TitlesOnly -> Unit
    }
}
