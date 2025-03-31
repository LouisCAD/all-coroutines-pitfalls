package content.pitfalls

import dsl.SlidesBuilder
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

fun SlidesBuilder.breakingStructuredConcurrency() {
    "Jobless CoroutineScope".slide {
        ""()
    }
    "Overwritten Job".slide {
        "It's like adding a branch that's not actually owned by the tree"()
        "Consequences" {
            "Cutting the parent/children relationship"()
        }
    }
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
