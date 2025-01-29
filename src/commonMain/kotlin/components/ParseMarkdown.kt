package components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

/**
 * Parses a given markdown text and converts it into an [AnnotatedString] with appropriate styles.
 *
 * Supports bold, italic, and striketrough.
 *
 * @param markdownText The input markdown text to parse.
 * @return An [AnnotatedString] with styles applied according to the markdown syntax.
 */
fun parseMarkdown(
    markdownText: String,
    boldWeight: FontWeight = FontWeight.SemiBold,
    codeFontFamily: FontFamily = FontFamily.Monospace,
    codeBackground: Color = Color.LightGray,
): AnnotatedString {
    val resultBuilder = AnnotatedString.Builder()

    markdownText.lineSequence().forEach { line ->
        textMarkDown(line, resultBuilder, boldWeight, codeFontFamily, codeBackground)
        resultBuilder.appendLine()
    }
    return resultBuilder.toAnnotatedString().trim() as AnnotatedString
}

/**
 * Converts markdown-style text formatting to [AnnotatedString] with appropriate [SpanStyle]s.
 *
 * @param inputText The input text to be converted.
 * @param resultBuilder The [AnnotatedString.Builder] to append the converted text to.
 * @return The converted text with markdown formatting replaced by appropriate [SpanStyle]s.
 */
private fun textMarkDown(
    inputText: String,
    resultBuilder: AnnotatedString.Builder,
    boldWeight: FontWeight,
    codeFontFamily: FontFamily,
    codeBackground: Color,
) {
    val boldItalicPattern = Regex("\\*\\*[*_](.*?)[*_]\\*\\*")
    val boldPattern = Regex("\\*\\*(.*?)\\*\\*")
    //    val italicPattern = Regex("[*_](.*?)[*_]") // *italic* disabled to avoid clashes with asterisks.
    val italicPattern = Regex("_(.*?)_") // _italic_ is unlikely to clash with actual content.
    val strikethroughPattern = Regex("~~(.+?)~~")
    val codeRegex = Regex("`(.*?)`")

    var currentIndex = 0

    while (currentIndex < inputText.length) {
        val nextBoldItalic = boldItalicPattern.find(inputText, startIndex = currentIndex)
        val nextBold = boldPattern.find(inputText, startIndex = currentIndex)
        val nextItalic = italicPattern.find(inputText, startIndex = currentIndex)
        val nextStrikethrough = strikethroughPattern.find(inputText, startIndex = currentIndex)
        val nextCode = codeRegex.find(inputText, startIndex = currentIndex)

        val nextMarkDown = listOfNotNull(
            nextBoldItalic,
            nextBold,
            nextItalic,
            nextStrikethrough,
            nextCode
        ).minByOrNull { it.range.first }

        if (nextMarkDown != null) {
            if (nextMarkDown.range.first > currentIndex) {
                // Append any normal text before the markdown
                val normalText = inputText.substring(currentIndex, nextMarkDown.range.first)
                resultBuilder.append(normalText)
            }

            val matchText = nextMarkDown.groupValues.getOrNull(1) ?: ""

            val style = when (nextMarkDown) {
                nextBoldItalic -> SpanStyle(
                    fontWeight = boldWeight,
                    fontStyle = FontStyle.Italic
                )
                nextBold -> SpanStyle(fontWeight = boldWeight)
                nextItalic -> SpanStyle(fontStyle = FontStyle.Italic)
                nextStrikethrough -> SpanStyle(textDecoration = TextDecoration.LineThrough)
                nextCode -> SpanStyle(fontFamily = codeFontFamily, background = codeBackground)
                else -> throw IllegalStateException("Unhandled markdown type")
            }

            resultBuilder.append(AnnotatedString(matchText, style))

            currentIndex = nextMarkDown.range.last + 1
        } else {
            // Append any remaining text if no more markdown found
            val normalText = inputText.substring(currentIndex)
            resultBuilder.append(normalText)
            currentIndex = inputText.length
        }
    }
}
