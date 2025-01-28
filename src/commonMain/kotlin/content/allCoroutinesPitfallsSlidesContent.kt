package content

import buildSlides
import dsl.SlidesBuilder
import dsl.TextContentKind

fun allCoroutinesPitfallsSlidesContent() = buildSlides {
    "🧨 All* coroutines pitfalls".slide(
        TextContentKind.PresentationOpening,
        subtitle = "Une présentation en franglais, proposée par Louis CAD"
    )
    coroutinesQuickIntro()
    pitfalls()
    finalAdvice()
    //TODO: Add conclusion and/or outro
}

private fun SlidesBuilder.finalAdvice() = "Final advice".slide {
    "📜 Before using anything" {
        "READ ITS DOC 😉"()
    }
    "❌ Don't eat `CancellationException" {
        "Always rethrow it"()
        "(just like that:  🤮)"()
    }
    "✅ Make sure you use the right `CoroutineScope`"()
    "💪 Leverage coroutines everywhere" {
        "Avoid ANRs!"()
        "Make users happy!"()
    }
    "🛡️ Try making good software" {
        "Reliability"()
        "Efficiency"()
        "Nice UX"()
    }
    "📢 Spread the word!"()
}
