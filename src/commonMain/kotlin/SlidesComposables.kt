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
    title = { parentTitles, slideTitle, content ->
        Title(parentTitles = parentTitles, slideTitle = slideTitle, content = content)
    },
    body = { content, step ->
        Body(
            modifier = Modifier.fillMaxWidth().weight(1f),
            content = content,
            step = step
        )
    }
)
