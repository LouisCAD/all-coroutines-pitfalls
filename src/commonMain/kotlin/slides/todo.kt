package slides

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import net.kodein.cup.Slide
import net.kodein.cup.ui.SpanStyleSheet
import net.kodein.cup.ui.styled
import net.kodein.cup.widgets.material3.BulletPoints
import org.kodein.emoji.Emoji
import org.kodein.emoji.activities.event.Sparkles
import org.kodein.emoji.compose.m3.TextWithNotoAnimatedEmoji


object MyStyleSheet : SpanStyleSheet() {
    val em by registerMarker(SpanStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 1.2.em
    ))
}

@Composable
fun Link(
    text: String,
    url: String
) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable {
                uriHandler.openUri(url)
            }
    )

}

val todo by Slide(
    stepCount = 4
) { step ->

    Text(
        styled(MyStyleSheet) { "There is ${+em}so much${-em} to do!" },
        style = MaterialTheme.typography.displayMedium
    )

    BulletPoints {
        BulletPoint(visible = step >= 1) {
            Row {
                Text("Read ")
                Link(text = "the documentation", url = "https://kosi-libs.github.io/CuP")
            }
        }
        BulletPoint(visible = step >= 2) {
            Row {
                Text("Explore ")
                Link(text = "a demo presentation", url = "https://github.com/kosi-libs/CuP/tree/main/demo")
            }
        }
    }

    AnimatedVisibility(
        visible = step >= 3,
        enter = scaleIn(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow)) + expandVertically(clip = false)
    ) {
        TextWithNotoAnimatedEmoji(
            text = "${Emoji.Sparkles} Start creating! ${Emoji.Sparkles}",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }

}
