package components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

@Composable
fun Txt(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle
) {
    Text(
        text = text.withMarkdownFormatting(),
        modifier = modifier,
        style = style
    )
}

@Composable
fun String.withMarkdownFormatting(): AnnotatedString {
    val codeBg = MaterialTheme.colorScheme.surfaceContainerHigh
    return remember(this, codeBg) {
        parseMarkdown(
            markdownText = this,
            codeBackground = codeBg
        )
    }
}
