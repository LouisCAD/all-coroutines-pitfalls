import all_coroutines_pitfalls.generated.resources.Res
import all_coroutines_pitfalls.generated.resources.cup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import net.kodein.cup.Presentation
import net.kodein.cup.Slide
import net.kodein.cup.Slides
import net.kodein.cup.cupApplication
import net.kodein.cup.laser.laser
import net.kodein.cup.speaker.speakerWindow
import net.kodein.cup.ui.styled
import net.kodein.cup.widgets.material3.BulletPoints
import net.kodein.cup.widgets.material3.cupScaleDown
import org.jetbrains.compose.resources.painterResource
import org.kodein.emoji.Emoji
import org.kodein.emoji.activities.event.Sparkles
import org.kodein.emoji.compose.EmojiService
import org.kodein.emoji.compose.m3.TextWithNotoAnimatedEmoji
import org.kodein.emoji.compose.m3.TextWithPlatformEmoji
import org.kodein.emoji.smileys_emotion.face_smiling.Wink
import slides.Link
import slides.MyStyleSheet
import slides.MyStyleSheet.em
import slides.intro
import slides.todo
import kotlin.reflect.KProperty


fun main() = cupApplication(
    title = "🧨 All coroutines pitfalls"
) {
    remember {
        // https://github.com/kosi-libs/Emoji.kt?tab=readme-ov-file#initializing-the-emoji-service
        EmojiService.initialize()
    }

    PresentationTheme {
        Presentation(
            slides = presentationSlides,
            configuration = {
                // TODO: Configure plugins
                speakerWindow()
                laser()
            },
            backgroundColor = MaterialTheme.colorScheme.background
        ) { slidesContent ->
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onBackground
            ) {
                slidesContent()
            }
        }
    }
}

// TODO: Write your own slides!
val presentationSlides = Slides(
    intro,
    todo
)

val theSlides = Slides(
    *createSlides(List(10) { it }, stepCount = { if (it % 2 != 0) 4 else 0}) { data, step ->
        if (data % 2 == 0) {

        Image(
            painterResource(Res.drawable.cup),
            contentDescription = "Compose ur Pres",
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
        )

        Text(
            text = "🧨 All* coroutines pitfalls",
            style = MaterialTheme.typography.displayLarge
        )
        TextWithPlatformEmoji(styled { "Welcome to ${+b}Compose ur Pres${-b}! ${Emoji.Wink}" })
        } else {
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
                enter = scaleIn(spring(Spring.DampingRatioHighBouncy, Spring.StiffnessMediumLow)) + expandVertically(clip = false)
            ) {
                TextWithNotoAnimatedEmoji(
                    text = "${Emoji.Sparkles} Start creating! ${Emoji.Sparkles}",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }.toTypedArray()
)

private val p = 0

fun <T> createSlides(
    slidesData: List<T>,
    stepCount: (data: T) -> Int,
    content: @Composable ColumnScope.(data: T, step: Int) -> Unit
): List<Slide> = slidesData.mapIndexed { index, data ->
    val slideProvider = Slide(
        stepCount = stepCount(data)
    ) { step ->
        content(data, step)
    }
    val fakeProperty = object : KProperty<Slide> by ::p {
        override val name: String = "#$index"
    }
    slideProvider.provideDelegate(null, fakeProperty).getValue(null, fakeProperty)
}
