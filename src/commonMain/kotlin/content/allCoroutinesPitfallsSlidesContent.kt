package content

import buildSlides
import dsl.TextContentKind

fun allCoroutinesPitfallsSlidesContent() = buildSlides {
    "🧨 All* coroutines pitfalls".slide(
        TextContentKind.PresentationOpening,
        subtitle = "Une présentation en franglais, proposée par Louis CAD"
    )
    coroutinesQuickIntro()
    pitfalls()
    //TODO: Add conclusion and/or outro
}
