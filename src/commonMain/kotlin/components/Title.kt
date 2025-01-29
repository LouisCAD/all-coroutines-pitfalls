package components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dsl.TextContentKind
import dsl.model.SlideContent
import dsl.model.SlideTitle

@Composable
fun Title(
    parentTitles: List<SlideTitle>,
    slideTitle: SlideTitle?,
    content: SlideContent.SingleElement?
) = Column(
    modifier = Modifier.fillMaxWidth().let { if (content == null) it else it.fillMaxHeight() },
//    verticalArrangement = Arrangement.spacedBy(4.dp)
) {
    val targetTitle: SlideTitle?
    val targetParentTitles: List<String> = if (slideTitle == null) {
        targetTitle = parentTitles.lastOrNull()
        parentTitles.dropLast(1)
    } else {
        targetTitle = slideTitle
        parentTitles
    }.map { it.smallTitle ?: it.text }.filter { it.isNotEmpty() }

    val parentTitlesTextStyle = MaterialTheme.typography.titleSmall
    val titleTextStyle = MaterialTheme.typography.displayLarge
    val subtitleTextStyle = MaterialTheme.typography.headlineSmall
    if (targetParentTitles.isNotEmpty()) {
        Txt(
            text = targetParentTitles.joinToString(separator = " > "),
            style = parentTitlesTextStyle,
            fontStyle = FontStyle.Italic
        )
        Spacer(Modifier.height(8.dp))
    }
    if (content != null) {
        val textStyle = when (content.contentKind) {
            TextContentKind.BigFact -> MaterialTheme.typography.displayLarge
            TextContentKind.CenteredTitle -> MaterialTheme.typography.displayLarge
            TextContentKind.NewSection -> MaterialTheme.typography.displayLarge
            TextContentKind.PresentationOpening -> MaterialTheme.typography.displayLarge
        }
        //TODO: Do what's right outside, or here, or both

    }
    targetTitle?.let {
        Txt(
            text = it.text,
            Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            style = titleTextStyle
        )
        it.subtitle?.let { text ->
            Txt(
                text = text,
                Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                style = subtitleTextStyle
            )
        }
    }
}
