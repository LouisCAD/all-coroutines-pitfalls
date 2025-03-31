package content.basics

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

private suspend fun scenario1() = coroutineScope {
    val someScope = this
    val just2Sec = someScope.launch {
        delay(2.seconds)
    }

    just2Sec.join()

}

private suspend fun scenario2() = coroutineScope {
    val someScope = this
    val just2Sec = someScope.launch(start = CoroutineStart.LAZY) {
        delay(2.seconds)
    }

    just2Sec.join()

}

private suspend fun scenario3() = coroutineScope {
    val someScope = this
    val just2Sec: Deferred<Unit> = someScope.async(start = CoroutineStart.LAZY) {
        delay(2.seconds)
    }

    just2Sec.join()

}

private suspend fun a() = coroutineScope {
    val someScope = this
    val job = someScope.launch(start = CoroutineStart.LAZY) {}
    val deferred = someScope.async(start = CoroutineStart.LAZY) {}
    job.join()
}
