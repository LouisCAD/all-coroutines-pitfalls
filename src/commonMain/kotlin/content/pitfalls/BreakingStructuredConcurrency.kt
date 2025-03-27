package content.pitfalls

import dsl.SlidesBuilder
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

fun SlidesBuilder.breakingStructuredConcurrency() {
    "Jobless CoroutineScope".slide {
        ""()
    }
    "Overwrite".slide()
}

private suspend fun doStuff() = coroutineScope {
    launch {  }.children.flatMap { it.children }.flatMap { it.children }
    val someScope = this
    someScope.launch {
        someSuspendFunction()
    }
    someScope.launch {
        launch {
            anotherThingToDoConcurrently()
        }
        someSuspendFunction()
    }
    coroutineScope {
        launch {
            someSuspendFunction()
        }
        launch {
            someSuspendFunction()
        }
    }
}

suspend fun anotherThingToDoConcurrently() {
    TODO("Not yet implemented")
}

private suspend fun someSuspendFunction() {
    yield()
}
