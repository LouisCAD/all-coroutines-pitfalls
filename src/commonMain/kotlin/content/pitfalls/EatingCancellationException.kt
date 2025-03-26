package content.pitfalls

import dsl.Disposition.Bullets
import dsl.SlidesBuilder
import dsl.TextContentKind
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive

fun SlidesBuilder.eatingCancellationException() {
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

private suspend fun doCoolThingWrong(someData: ByteArray) {
    try {
        sendToBackend(someData)
    } catch (e: Exception) {
        reportErrorSomehow(e)
    }
}

private suspend fun doCoolThingWrong2(someData: ByteArray) {
    try {
        sendToBackend(someData)
    } catch (e: Exception) {
        reportErrorSomehow(e)
    } catch (e: CancellationException) {
        throw e
    }
}

private suspend fun doCoolThingRight(someData: ByteArray) {
    try {
        sendToBackend(someData)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        reportErrorSomehow(e)
    }
}

private suspend fun doCoolThingRight2(someData: ByteArray) {
    try {
        sendToBackend(someData)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        reportErrorSomehow(e)
    }
}

private suspend fun doCoolThingRight3(someData: ByteArray) {
    try {
        sendToBackend(someData)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        if (e is CancellationException) throw e
        reportErrorSomehow(e)
    }
}

///////////////////////////////////////////

private suspend fun doCoolThingIseWrong(someData: ByteArray) {
    try {
        sendToBackend(someData)
    } catch (e: IllegalStateException) {
        reportErrorSomehow(e)
    } catch (e: CancellationException) {
        throw e
    }
}

private suspend fun doCoolThingIseRight(someData: ByteArray) {
    try {
        sendToBackend(someData)
    } catch (e: CancellationException) {
        throw e
    } catch (e: IllegalStateException) {
        reportErrorSomehow(e)
    }
}

///////////////////////////////////////////

private suspend fun doCoolThingRunCatchingWrong(someData: ByteArray): Result<Unit> {
    return runCatching {
        sendToBackend(someData)
    }
}

private suspend fun doCoolThingRunCatchingRight(someData: ByteArray): Result<Unit> {
    return runCatching {
        sendToBackend(someData)
    }.onFailure { t: Throwable ->
        if (t is CancellationException) throw t
    }
}

private suspend fun doCoolThingRunCatchingRight2(someData: ByteArray): Result<Unit> {
    return runCatching {
        sendToBackend(someData)
    }.cancellable()
}

@Suppress("RedundantSuspendModifier")
suspend inline fun <T> Result<T>.cancellable(): Result<T> = onFailure {
    if (it is CancellationException) throw it
}

///////////////////////////////////////////

private suspend fun doCoolThingWithLoopWrong(someData: ByteArray) {
    while (true) try {
        awaitNetworkConnected()
        sendToBackend(someData)
        return
    } catch (e: Exception) {
        reportErrorSomehow(e)
        continue // Retry
    }
}

private suspend fun doCoolThingWithLoopWrong2(someData: ByteArray) {
    while (true) try {
        currentCoroutineContext().ensureActive()
        awaitNetworkConnected()
        sendToBackend(someData)
        return
    } catch (e: Exception) {
        reportErrorSomehow(e)
        continue // Retry
    }
}

private suspend fun doCoolThingWithLoopCorrect(someData: ByteArray) {
    while (true) try {
        awaitNetworkConnected()
        sendToBackend(someData)
        return
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        reportErrorSomehow(e)
        continue // Retry
    }
}

private suspend fun doCoolThingWithLoopCorrect2(someData: ByteArray) {
    while (true) try {
        awaitNetworkConnected()
        sendToBackend(someData)
        return
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        reportErrorSomehow(e)
        continue // Retry
    }
}


/////////////////////////////////////////// Supporting symbols

private fun reportErrorSomehow(e: Throwable) {
    TODO("Not yet implemented")
}

private suspend fun sendToBackend(data: ByteArray) {

}

private suspend fun awaitNetworkConnected() {
    TODO("Not yet implemented")
}
