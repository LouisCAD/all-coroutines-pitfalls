import all_coroutines_pitfalls.generated.resources.*
import all_coroutines_pitfalls.generated.resources.Res
import all_coroutines_pitfalls.generated.resources.mrb_1
import all_coroutines_pitfalls.generated.resources.mrb_2
import all_coroutines_pitfalls.generated.resources.mrb_3
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import content.allCoroutinesPitfallsSlidesContent
import net.kodein.cup.*
import net.kodein.cup.laser.laser
import net.kodein.cup.speaker.speakerWindow
import org.jetbrains.compose.resources.painterResource
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
            slides = Slides(mrbIntroSlides() + allCoroutinesPitfallsSlidesContent()),
            configuration = {
                defaultSlideSpecs = SlideSpecs.default(LayoutDirection.Ltr).copy(
                    size = DpSize(width = 640.dp, height = 360.dp) * 1.2f
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

private fun mrbIntroSlides(): List<Slide> = listOf(
    Res.drawable.mrb_1,
    Res.drawable.mrb_2,
    Res.drawable.mrb_3,
    Res.drawable.mrb_4,
).mapIndexed { index, drawableResource ->
    Slide(
        name = "MRB intro #${index + 1}"
    ) {
        Image(
            painter = painterResource(drawableResource),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
