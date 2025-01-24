package slides

import coroutinesQuickIntro
import dsl.TextContentKind
import pitfalls
import buildSlides

fun allCoroutinesPitfallsSlidesContent() = buildSlides {
    "All* coroutines pitfalls".slide(
        TextContentKind.PresentationOpening,
        subtitle = "Une présentation en franglais, proposée par Louis CAD"
    )
    coroutinesQuickIntro()
    pitfalls()
    //TODO: Add conclusion and/or outro
}
