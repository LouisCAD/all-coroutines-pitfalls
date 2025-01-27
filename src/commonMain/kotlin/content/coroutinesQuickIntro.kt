package content

import dsl.Disposition.Bullets
import dsl.SideBySideDelivery
import dsl.SlidesBuilder
import dsl.TextContentKind

fun SlidesBuilder.coroutinesQuickIntro() {
    definitions()
    whyUseCoroutines()
    "Coroutines are a _fundamentally_ new way to do all things asynchronous".slide(TextContentKind.BigFact)
    kindsOfPitfalls()
    quickCoroutinesBasics()
}

private fun SlidesBuilder.definitions() {
    "Let's talk content.definitions".slidesGroup(
        subtitle = "To make sure we're on the same page",
    ) {
        "What is a coroutine?".slide(Bullets) {
            "co-routine = cooperative routine" {
                "routine : some code (i.e. a function) being executed"()
            }
            "Coroutines cooperate to run without blocking each other"()
            "It's a suspendable function, while it's being run"()
        }
        sideBySide(delivery = SideBySideDelivery.PerLine) {
            "routine".slide(Bullets) {
                "The calling thread is blocked/monopolized until it's done" {
                    "Callbacks are required for asynchronous operations"()
                }
            }
            "coroutine".slide(Bullets) {
                "Can suspend its execution to let other ones run, and resume later on" {
                    "Callbacks are *not* needed"()
                }
            }
        }
        "Why do we want to avoid callbacks?".slide(Bullets) {
            "They make everything more complicated!"()
            "Logic is spread all over the place" {
                "The code can't be read linearly"()
                "Tracking the progress of a chain of callbacks is hard"()
                "Editing a chain of callbacks without introducing bugs is even harder"()
                "Introducing conditions also increases complexity significantly"()
            }
            "Indentation can increase a lot, and readability suffers"()
            "Tracking errors is tedious"()
            "Handling errors is even harder (a nightmare, to put it lightly)"()
        }
    }
}

private fun SlidesBuilder.whyUseCoroutines() {
    "Why use coroutines? My reasons".slide(Bullets.Numbers) {
        "Avoid callbacks… and all their problems !"()
        "Get back to the simplicity of sequential code"()
        "Make saving resources easier"()
        "Open new ways of programming"()
    }
}

private fun SlidesBuilder.quickCoroutinesBasics() {
    "Basics in 5 minutes".slidesGroup(
        smallTitle = "Basics",
        subtitle = "Before showing you all the wrong ways, let's try an attempt at showing you the right way",
    ) {
        "suspend functions".slidesGroup(
            disposition = Bullets,
        ) {
            "What they look like".slide {
                "They have the **suspend** modifier"()
                //TODO: have a bunch of suspend function signatures pop-up, with redacted bodies (or not, for very short ones?)
                "They complete only once their \"work\" is:" {
                    "done"()
                    "failed"()
                    "cancelled"(sideLabel = "More on that later…")
                }
            }
            "How they work".slide {
                "They are compiled to normal functions…"()
                "They are compiled to normal functions…"()
            }
            ""
        }
        "suspend funs' hidden power".slide {
            "cancellation"()
        }
    }
}
