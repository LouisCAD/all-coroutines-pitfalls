package content

import buildSlides
import dsl.Disposition
import dsl.SlidesBuilder
import dsl.TextContentKind

fun allCoroutinesPitfallsSlidesContent() = buildSlides {
    "🧨 All* coroutines pitfalls".slide(
        TextContentKind.PresentationOpening,
        subtitle = "Une présentation en franglais, proposée par Louis CAD"
    )
    getReady()
    coroutinesQuickIntro()
    //TODO: Pitfalls != Mistakes
    preliminaryAdvice()
    pitfalls()
    mistakes() //Not exhaustive, and not too much in depth
    finalAdvice()
    //TODO: Add conclusion and/or outro
}

private fun SlidesBuilder.getReady() {
    "Turn on your brain! 🧠⚡️".slide(
        Disposition.List.BigCentered,
        subtitle = "Friendly disclaimer | Setting the expectations"
    ) {
        "Very technical" {
            "Hopefully very helpful too"()
        }
        "First time I make this presentation" {
            "Lacks illustrations"()
        }
        "Dozens of pitfalls to cover"()
        "Only 40 minutes"()
    }
}

private fun SlidesBuilder.preliminaryAdvice() = "Final advice".slide {
    "📜 Before using anything" {
        "READ ITS DOC 😉"()
    }
    "🛡️ Try making good software" {
        "Reliability"()
        "Efficiency"()
        "Nice UX"()
    }
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
