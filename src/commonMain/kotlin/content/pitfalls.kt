package content

import dsl.Disposition
import dsl.Disposition.Bullets
import dsl.SlidesBuilder

fun SlidesBuilder.pitfalls() {
    kindsOfPitfalls(startCentered = true)
    pitfallsPartOne()
}

fun SlidesBuilder.kindsOfPitfalls(startCentered: Boolean = false) {
    "4 kinds of pitfalls".slide(Bullets.Abc, startCentered = startCentered) {
        "You don't know **what you want to do** exactly"()
        "You don't really know **what you're using**"()
        "You didn't rethrow CancellationException"(sideLabel = "most common one!")
        "You forgot something while doing a minor refactoring"()
    }
}

private fun SlidesBuilder.pitfallsPartOne() = "Coroutines pitfalls (part 1)".slidesGroup(
    disposition = Bullets.Numbers,
    smallTitle = ""
) {
    "Incorrect handling of errors/exceptions/throwables".slide {
        "Catch CancellationException"()
        "Catch Exceptions/Throwables at the wrong place" {
            "around launch instead of inside"()
            "around await instead of…"()
            "around coroutineScope"()
            "inside async"()
        }
        "using async + await immediately instead of withContext"()
    }
    "Incorrect use of async + await".slide {
        "using async + await immediately instead of withContext"()
    }
    "Improper bridging".slidesGroup {
        "Missing/breaking cancellation support".slide {
            "Using `suspendCoroutine` instead of `suspendCancellableCoroutine`"()
            "Forgetting to forward the cancellation signal"()
        }
        "Call resume() more than once".slide {
            "Using `suspendCancellableCoroutine` instead of `callbackFlow` for repeat callbacks"()
        }
    }
    "Underuse".sideBySide {
        "Avoid".slide {
            "Using blocking APIs"()
            "Keeping callbacks in your business logic"()
        }
        "Prefer".slide {
            "Using proper suspend alternatives"()
            "Using callback APIs, with proper bridging"()
        }
    }
    "👶 ➡️ 🍽️ ➡️ 🐺 Orphan coroutines eaten".slidesGroup {
        "🚛 🗑️ Don't let your coroutines be garbage collected!".slide {
            
        }
    }
    "Wrong Dispatcher".slidesGroup {
        "Underuse Dispatchers.IO".sideBySide {
            //TODO: Fill content
        }
        "Overuse Dispatchers.IO".sideBySide {
            "Wrong use".slide {
                "Non blocking I/O"()
                "CPU-bound work"()
                "Everything, just in case"()
            }
            "Correct usage".slide {
                "Wrapping blocking I/O sections" {
                    "File I/O"()
                    "File I/O"()
                }
                "Wrapping IPC calls" {
                    "Some system calls, often in Android's `Managers`"(sideLabel = "Inter-Process Communication") {
                        "packageManager"()
                        "DownloadManager"()
                        "…"()
                    }
                    "Jump into the source to see if it's connecting to a Service"()
                }
            }
        }
        //TODO: Elaborate
        //TODO: Add bad examples, with correction/good examples.
    }
    "🤪 Making a messed-up CoroutineContext".slide {
        "Replacing the `Job` of a `CoroutineContext`" {
            "It disconnects it from the original hierarchy, and breaking the structured concurreny chain"()
        }
    }
    "CoroutineScope pitfalls".slidesGroup(disposition = Bullets) {
        "Using the wrong `CoroutineScope`".slide {
            "Many ways to get a `CoroutineScope`"()
            "Only a few ways to get the right one"()
        }
        "Confusing coroutineScope and CoroutineScope".slide {
            //TODO: Illustrate
        }
        "launch a coroutine in a scope being cancelled".slide {
            //TODO: Illustrate
        }
        "Using GlobalScope".slide {

        }
    }
    "Wrong function signatures".slide {
        "Missing the point: Suspend functions that take success and failure callbacks"()
        "Appending `Async` to suspend function names"()
        "Forgetting the `Async` suffix for functions that return a Deferred"()
        "A suspending function that returns a Flow"()

    }
    "Concurrency not thought through".slide {
        "Unintendedly have the same function run twice in parallel"()
    }
}
