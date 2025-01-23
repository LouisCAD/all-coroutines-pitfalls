import dsl.Disposition
import dsl.Disposition.Bullets
import dsl.SlidesBuilder
import dsl.TextContentKind

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

private fun SlidesBuilder.pitfallsPartOne() {
    TODO()
}