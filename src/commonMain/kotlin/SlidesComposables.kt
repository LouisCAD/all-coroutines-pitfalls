import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import components.Body
import components.Title
import dsl.SlidesBuilder
import dsl.builder.SlidesDataBuilder
import net.kodein.cup.Slide

fun buildSlides(
    slidesMaker: CupSlidesMaker = defaultCupSlidesMaker,
    builder: SlidesBuilder.() -> Unit
): List<Slide> {
    return slidesMaker.buildSlides(SlidesDataBuilder().apply(builder).build())
}

val defaultCupSlidesMaker: CupSlidesMaker = DefaultCupSlidesMaker(
    title = { parentTitles, slideTitle ->
        Title(parentTitles = parentTitles, slideTitle = slideTitle)
    },
    body = { parentTitles, currentTitle, content, step ->
        Body(
            modifier = Modifier.fillMaxWidth().weight(1f),
            parentTitles = parentTitles,
            currentTitle = currentTitle,
            content = content,
            step = step
        )
    }
)
