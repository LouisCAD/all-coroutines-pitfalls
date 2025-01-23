package slides

import coroutinesQuickIntro
import dsl.TextContentKind
import pitfalls
import slides

fun allCoroutinesPitfallsSlidesContent() = slides {
    "All* coroutines pitfalls".slide(
        TextContentKind.PresentationOpening,
        subtitle = "Une présentation en franglais, proposée par Louis CAD"
    )
    coroutinesQuickIntro()
    pitfalls()
    //TODO: Add conclusion and/or outro
}
