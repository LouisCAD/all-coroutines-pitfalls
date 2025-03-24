package content

import dsl.Disposition.Bullets
import dsl.SideBySideDelivery
import dsl.SlidesBuilder
import dsl.TextContentKind

fun SlidesBuilder.coroutinesQuickIntro() {
    definitions()
    whyUseCoroutines()
    "Coroutines are a _fundamentally_ new way to\ndo all things asynchronous".slide(TextContentKind.BigFact)
    kindsOfPitfalls()
    quickCoroutinesBasics()
}

private fun SlidesBuilder.definitions() {
    "Let's talk **definitions**".slidesGroup(
        smallTitle = "Definitions",
        subtitle = "To make sure we're on the same page",
    ) {
        "What is a coroutine?".slide(Bullets) {
            "co-routine = cooperative routine" {
                "routine : some code (i.e. a function) being executed"()
            }
            "Coroutines cooperate to run without blocking each other"()
            "It's a suspendable function, while it's being run"()
        }
        sideBySide(
            delivery = SideBySideDelivery.PerLine,
            overviewTitle = "routine vs coroutine"
        ) {
            "routine".slide(Bullets) {
                "The calling thread is **blocked**" {
                    "Monopolizing: Only one per thread"()
                    "**Callbacks** are required for async work"()
                }
                "Pretty cool 👍"()
            }
            "coroutine".slide(Bullets) {
                "Can **suspend** its execution, and **resume** later on" {
                    "Lets **other ones run** while suspended"()
                    "**Callbacks not needed**"()
                }
                "Really better 👍👍👍"()
            }
        }
        "Why do we want to avoid callbacks?".slide(Bullets) {
            //TODO[illustration]: Messy cables linked together, or messy labels
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
    "Why use coroutines? My 3 reasons".slidesGroup(Bullets.Numbers) {
        "Avoid callbacks".slide(
            TextContentKind.BigFact,
            subtitle = "Get back to the simplicity of sequential code"
        )
        //TODO[illustration]: messy XX linked together, on the start side,
        // and show a clean, linear/sequential version on the end side.
        "Make saving resources easier".slide()
        "Open new ways of programming".slide()
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
                "that can be reentered multiple times…"()
                "until done, failed, or cancelled…"()
            }
            "Their hidden power: cancellation".slide {
                "brought by kotlinx.coroutines"()
                "propagates automatically"()
                "powered by `Job`"()
            }
        }
        "What's a coroutine again?".slide {
            "A coroutine is an instance of a suspendable computation"()
        }
        "How to start a coroutine?".slide(Bullets.Numbers) {
            //TODO[illustration]: Add snippets that show correct ways of getting a scope
            "Get a CoroutineScope"()
            //TODO[illustration]: Add 1 tangible snippet using launch
            //TODO[illustration]: Add 1 tangible snippet using coroutineScope + async (example from SwissTransfer getting the name and size?)
            "Call `launch { … }` or `async { … }`"()
        }
        "How to stop a coroutine?".slide {
            "Call `cancel()` at the right place"()
            "Use a construct that will do it automatically" {
                "`***Latest` `Flow` operators"()
                "`raceOf(…)`"()
            }
        }
        //TODO: TK?
    }
}
