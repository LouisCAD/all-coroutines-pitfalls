package slides

fun allCoroutinesPitfallsSlidesContent() = slides {
    "All* coroutines pitfalls" {
        subtitle("Une présentation en franglais, proposée par Louis CAD")
    }
    "Let's talk definitions".slidesGroup {
        subtitle("To make sure we're on the same page")
        "What is a coroutine?".slide(Bullets) {
            "co-routine = cooperative routine" {
                "routine : some code (i.e. a function) being executed"()
            }
            "Coroutines cooperate to run without blocking each other"()
            "It's a suspendable function, while it's being run"()
        }
        comparison {
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
    "Why use coroutines? My reasons".slide(Bullets.Numbers) {
        "Avoid callbacks… and all their problems !"()
        "Get back to the simplicity of sequential code"()
        "Make saving resources easier"()
        "Open new ways of programming"()
    }
    "Coroutines are a _fundamentally_ new way to do all things asynchronous".slide(BigFact)
    kindsOfPitfalls()
    "Basics in 5 minutes".slidesGroup(smallTitle = "Basics") {
        subtitle("Before showing you all the wrong ways, let's try an attempt at showing you the right way")
        "suspend functions".slidesGroup(Bullets) {
            "What they look like" {
                "They have the **suspend** modifier"()
                //TODO: have a bunch of suspend function signatures pop-up, with redacted bodies (or not, for very short ones?)
                "They complete only once their \"work\" is:" {
                    "done"()
                    "failed"()
                    "cancelled"(sideLabel = "More on that later…")
                }
            }
            "How they work" {
                "They are compiled to normal functions…"()
                "They are compiled to normal functions…"()
            }
            ""
        }
        "suspend funs' hidden power" {
            "cancellation"()
        }
    }
}

fun SlidesBuilder.kindsOfPitfalls() {
    "4 kinds of pitfalls".slide(Bullets.Abc) {
        "You don't know **what you want to do** exactly"()
        "You don't really know **what you're using**"()
        "You didn't rethrow CancellationException"(sideLabel = "most common one!")
        "You forgot something while doing a minor refactoring"()
    }
}
