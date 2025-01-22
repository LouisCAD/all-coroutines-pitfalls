package slides

fun allCoroutinesPitfallsSlidesContent() = slides {
    "All* coroutines pitfalls" {
        subtitle("Une présentation en franglais, proposée par Louis CAD")
    }
    "Let's talk definitions".slidesGroup {
        subtitle("To make sure we're on the same page")
        "What is a coroutine?".bullets {
            "co-routine = cooperative routine" {
                "routine : some code (i.e. a function) being executed"()
            }
            "Coroutines cooperate to run without blocking each other"()
            "It's a suspendable function, while it's being run"()
        }
        comparison {
            "routine".bullets {
                "The calling thread is blocked/monopolized until it's done" {
                    "Callbacks are required for asynchronous operations"()
                }
            }
            "coroutine".bullets {
                "Can suspend its execution to let other ones run, and resume later on" {
                    "Callbacks are *not* needed"()
                }
            }
        }
        "Why do we want to avoid callbacks?".bullets {
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
    "Why use coroutines? My reasons".bullets(mode = Bullets.Numbers) {
        "Avoid callbacks… and all their problems !"()
        "Get back to the simplicity of sequential code"()
        "Make saving resources easier"()
        "Open new ways of programming"()
    }
}