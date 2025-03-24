package content

import dsl.Disposition.Bullets
import dsl.SlidesBuilder
import pitfallsInKotlinxCoroutinesLib

fun SlidesBuilder.pitfalls() {
    kindsOfPitfallsOverview(startCentered = true)
    pitfallsInKotlinxCoroutinesLib("Chapter 1: pitfalls in the kotlinx.coroutines library")
    pitfallsInStdlibKotlinCoroutinesPackage("Chapter 2: pitfalls in stdlib's kotlin.coroutines package")
}

fun SlidesBuilder.kindsOfPitfallsOverview(startCentered: Boolean = false) {
    "Pitfalls are found in".slide(Bullets.Numbers, startCentered = startCentered) {
        "kotlinx.coroutines _(the official library)_"()
        "kotlin.coroutines _(the Kotlin stdlib package)_"()
        "Everywhere else"(Bullets.Abc) {
            "stealthily blocking APIs"()
            "asynchronous APIs"()
            "poorly implemented suspend functions or Flows"()
        }
    }
}

