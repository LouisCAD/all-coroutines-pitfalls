package content

import dsl.Disposition
import dsl.Disposition.Bullets
import dsl.SlidesBuilder
import dsl.TextContentKind

fun SlidesBuilder.pitfalls() {
    kindsOfPitfalls(startCentered = true)
    pitfallsPartOne("Part 1: Coroutines pitfalls")
    flowPitfalls("Part 2: Coroutines' Flows pitfalls")
}

fun SlidesBuilder.kindsOfPitfalls(startCentered: Boolean = false) {
    "4 kinds of pitfalls".slide(Bullets.Abc, startCentered = startCentered) {
        "You don't know **what you want to do** exactly"()
        "You don't really know **what you're using**"()
        "You didn't rethrow `CancellationException`"(sideLabel = "most common one!")
        "You forgot something while doing a minor refactoring"()
    }
}

private fun SlidesBuilder.pitfallsPartOne(title: String) = title.slidesGroup(
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
            "Forgetting to forward the cancellation signal" {
                "2 solutions:"(Bullets.Abc) {
                    "Use `continuation.invokeOnCancellation { … }`"()
                    "Wrap it all with a `try` block, and cancel in the `finally` block"()
                }
            }
        }
        "Call resume() more than once".slide {
            "Problem:"(Bullets) {
                "You have a **repeat callback**"()
                "You use `suspendCancellableCoroutine`"()
                "The callback fires once"()
                "The callback fires _again_…"()
                "Fire 🔥"()
            }
            "Solution:"(Bullets) {
                "Use `callbackFlow`"()
            }
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
    "❌❌❌ Wrong Dispatcher".slidesGroup(
        smallTitle = "Wrong Dispatcher"
    ) {
        "The 3-ish Dispatchers that matter".slide(Bullets) {
            "Dispatchers.Main" {
                "Touching the UI"()
                "Synchronizing state on a single thread"()
            }
            "Dispatchers.Default" {
                "CPU-bound work" {
                    "incl. heavy allocation"()
                }
            }
            "Dispatchers.IO" {
                "For **blocking** I/O"()
            }
        }
        "Underusing Dispatchers.IO".slide(Bullets) {
            "Using `Dispatchers.Default` or `Dispatchers.Main` for…" {
                "Blocking APIs that do I/O"()
                "Blocking APIs that _might_ do I/O"()
            }
        }
        "Overusing Dispatchers.IO".sideBySide {
            "Wrong use".slide {
                "Non blocking I/O"()
                "CPU-bound work"()
                "Everything, just in case"()
            }
            "Correct usage".slide {
                "Wrap blocking I/O sections"(Bullets) {
                    "File I/O"()
                    "Local database access"()
                    "Blocking I/O that has no asynchronous/suspending alternative"()
                }
                "Wrap IPC (Inter-Process Communication)"(Bullets) {
                    "Some system calls"()
                    "Communicating with other apps"()
                    "Peek at the source when possible"()
                }
            }
        }
        //TODO: Elaborate
        //TODO: Add bad examples, with correction/good examples.
    }
    "🤪 Making a messed-up CoroutineContext".slide {
        "Replacing the `Job` of a `CoroutineContext`"(Bullets) {
            "Disconnects the coroutine from the original hierarchy" {
                "Breaks \"structured concurreny\""()
                "Silently **prevents cancellation propagation**"()
                "Very very bad 👿 😵 💀"()
            }
        }
    }
    "CoroutineScope pitfalls".slidesGroup(disposition = Bullets) {
        "👶 ➡️ 🍽️ ➡️ 🐺 Orphan coroutines eaten".slidesGroup {
            "🚛 🗑️ Don't let your coroutines be garbage collected!".slide {
                "Some callback APIs might use `WeakReference` under the hood"(Bullets) {
                    "Like Android's" {
                        "MediaPlayer"()
                        "SharedPreferences"()
                    }
                }
                "Solutions"(Bullets) {
                    "Create a local `coroutineScope { … }`"()
                    "Use system integration scopes" {
                        "`lifecycleScope`"()
                        "`viewModelScope`"()
                    }
                    "Always make sure custom `CoroutineScope` are strongly referenced"()
                }
                //TODO: Explain the problem
            }
        }
        "Using GlobalScope".slide(disposition = Bullets) {
            "Bad father" {
                "Doesn't remember about children"()
                "Can't cancel child coroutines"()
            }
        }
        "Using the wrong `CoroutineScope`".slide {
            "Many ways to get a `CoroutineScope`"()
            "Only a few ways to get the right one"()
        }
        "Confusing coroutineScope and CoroutineScope".slide()
        "launching a coroutine in a scope being cancelled".slide()
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
    "Missing opportunities to improve perfs".slide(Bullets.Numbers) {
        "Embrace concurrency & parallelization"()
        "Use caching (with appropriate strategy) when possible"()
        "Consider pre-loading/fetching"()
    }
    "Missing existing coroutines integrations".slide(Bullets.Numbers) {
        "await() function in "()
        "Make façade APIs"()
        "Abstract away complex sequences"()
    }
    "Missing opportunities to simplify the code".slide(Bullets.Numbers) {
        "Extract functions"()
        "Make façade APIs"()
        "Abstract away complex sequences"()
    }
}

private fun SlidesBuilder.flowPitfalls(title: String) = title.slidesGroup(
    disposition = Bullets.Numbers,
    smallTitle = ""
) {
    "Emitting mutable objects".slide {
        //TODO: Illustrate with a bad example
        "Problems"(Bullets) {
            "Mutating doesn't change object identity" {
                "Changes don't get through `StateFlow` or `distinctUntilChanged()` 😶"()
            }
        }
        //TODO: Illustrate with a good example
        "Solution"(Bullets.Abc) {
            "Lose the habit of using non-observable mutable objects"()
            "Minimize the mutability scope" {
                "Keep the mutable object inside"()
                "Emit a defensive copy"()
            }
        }
    }
    "Events vs States".slidesGroup {
        "Confusing Events and States".slide(Bullets.Invisible) {
            "**Event**:" {
                "Happens, then becomes history."()
                "Something that **causes changes** to the state."()
            }
            "**State**:" {
                "How the system is at **any** given moment."()
            }
        }
        "Using the wrong sharing operator".slide {
            "Correct usage:"(Bullets.Invisible) {
                "`stateIn(…)`"(Bullets.Arrow) {
                    "For **States**"()
                }
                "`shareIn(…)`"(Bullets.Arrow) {
                    "Preferrably for **Events**"()
                    "Technically work for states too"()
                }
            }
        }
    }
    "Forgetting the terminal operator".slide {
        "Mistake examples"(Bullets) {
            "`transformLatest` without `first()`"()
            "`onEach` without `collect()` or something"()
        }
        "Example scenarios"(Bullets) {
            "…during refactoring" {
                "After replacing `collect` with `transform`"()
                "After replacing `collectLatest` with `transformLatest`"()
            }
        }
    }
    "Not understanding the operator".slide {
        "Notoriously tricky ones"(Bullets) {
            "`debounce(…)`"()
            "`scan(…)`"()
        }
        "Solution"(Bullets) {
            "Read the doc thoroughly 🤓"()
            "Compose your own operator, if none fits your needs"()
        }
    }
    "Not sharing a `Flow`".slide(Disposition.List.BigCentered) {
        "Whenever it makes sense…"()
        "use `stateIn` or `shareIn`"()
        "That will avoid duplicate work"()
    }
    "Read `value` on a cold `StateFlow".slide(contentKind = TextContentKind.NewSection)
}