import dsl.Disposition.Bullets
import dsl.SlidesBuilder
import dsl.TextContentKind

fun SlidesBuilder.pitfallsInKotlinxCoroutinesLib(title: String) = title.slidesGroup(
    disposition = Bullets.Numbers,
    smallTitle = ""
) {
    eatingCancellationException()
}

private fun SlidesBuilder.eatingCancellationException() {
    "Eating CancellationException".slidesGroup {
        sideBySide {
            slide {
                //TODO[illustration]: eating-batteries-innocent
                //TODO[snippet]: Highlight catch(e: Exception)
                //TODO[snippet]: Highlight error being logged/reported in the catch block
                //TODO: Show 3 ways of fixing it:
                // - Add `CancellationException` dedicated block (Best solution)
                // - Check if is `CancellationException` and rethrow
                // - Call `currentCoroutineContext().ensureActive()` first in the catch block
            }
            slide {
                //TODO: Show fix
            }
        }
        sideBySide {
            slide {
                //TODO[illustration]: LR44 batteries
                //TODO[snippet]: Have an infinite loop
                //TODO[snippet]: Highlight catch(e: IllegalStateException)
                //TODO[snippet]: Highlight "something went wrong, retrying" error being logged/reported in the catch block
                //TODO[snippet]: have the `continue` keyword

                //TODO[illustration]: Animate the throw/swallow loop 3 times, maybe with a (soft) video playing in reverse
            }
            slide {
                //TODO[illustration]: Cylindrical candy
                //TODO: Show multiple ways of fixing it:
                // - Rethrow `CancellationException` (Best solution)
                // - Call `currentCoroutineContext().ensureActive()` first in the catch block
                // - Call `currentCoroutineContext().ensureActive()` first in the loop
            }
        }
        sideBySide {
            slide {
                //TODO[illustration]: eating-batteries-small
                //TODO[snippet]: Highlight runCatching
                //TODO[snippet]: Highlight error being logged/reported in `onFailure`
            }
            slide {
                //TODO: Show fix with onFailure snippet, then morph it into use of declaration + use of `cancellable()`
            }
        }
        "Potential consequences".slidesGroup() {
            "Cluttering logs".slide {
                //TODO[illustration]: fun pollution background
                //TODO[illustrations]: logcat screenshots
                //TODO[illustrations]: Sentry screenshots
                "with `JobCancellationException`"()
                "with other `CancellationException`s"()
                "with custom \"unknown\" errors"()
            }
            "Freezes or crippling".slide(Bullets) {
                "infinite try/throw/catch loops"()
                "Consequences"(Bullets) {
                    "blocked main thread -> ANRs"()
                    "burnt CPU cores -> stealth ANR" {
                        "The UI stays responsive…"()
                        //TODO[illustration]: Disappointed black guy, or Traumatized Mr. Incredible
                        "but \"background\" logic is stuck"()
                        "Not detected by the system"()
                    }
                }
            }
        }
        "One shall not prevent cancellation from completing".slide() //TODO[illustration] old man with long beard
        "Always rethrow `CancellationException` **first**.".slide(contentKind = TextContentKind.BigFact)
        //TODO[illustration]: Show "think smart", "good boy", or other validation visual
    }
}
