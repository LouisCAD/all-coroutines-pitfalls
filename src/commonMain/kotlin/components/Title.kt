package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dsl.model.SlideTitle

@Composable
fun Title(
    parentTitles: List<SlideTitle>,
    slideTitle: SlideTitle?
) = Column(
    modifier = Modifier.fillMaxWidth(),
//    verticalArrangement = Arrangement.spacedBy(4.dp)
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
        Txt(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
    targetTitle?.let {
        Txt(
            text = it.text,
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displayLarge
        )
        it.subtitle?.let { text ->
            Txt(
                text = text,
                Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
