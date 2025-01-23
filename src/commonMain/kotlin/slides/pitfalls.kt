import dsl.Disposition
import dsl.Disposition.Bullets
import dsl.SlidesBuilder

fun SlidesBuilder.pitfalls() {

}

fun SlidesBuilder.kindsOfPitfalls() {
    "4 kinds of pitfalls".slide(Bullets.Abc) {
        "You don't know **what you want to do** exactly"()
        "You don't really know **what you're using**"()
        "You didn't rethrow CancellationException"(sideLabel = "most common one!")
        "You forgot something while doing a minor refactoring"()
    }
}
