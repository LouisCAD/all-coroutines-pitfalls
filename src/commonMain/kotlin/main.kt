import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.LayoutDirection
import content.allCoroutinesPitfallsSlidesContent
import net.kodein.cup.*
import net.kodein.cup.laser.laser
import net.kodein.cup.speaker.speakerWindow
import org.kodein.emoji.compose.EmojiService
import slides.intro
import slides.todo


fun main() = cupApplication(
    title = "🧨 All coroutines pitfalls"
) {
    remember {
        // https://github.com/kosi-libs/Emoji.kt?tab=readme-ov-file#initializing-the-emoji-service
        EmojiService.initialize()
    }

    PresentationTheme {
        Presentation(
//            slides = presentationSlides,
            slides = Slides(allCoroutinesPitfallsSlidesContent()),
            configuration = {
                defaultSlideSpecs = SlideSpecs.default(LayoutDirection.Ltr).copy(
                    size = SLIDE_SIZE_16_9
                )
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

// Example slides
val presentationSlides = Slides(
    intro,
    todo
)
