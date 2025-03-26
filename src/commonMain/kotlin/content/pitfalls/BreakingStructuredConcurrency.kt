package content.pitfalls

import dsl.SlidesBuilder

fun SlidesBuilder.breakingStructuredConcurrency() {
    "Jobless CoroutineScope".slide {
        ""()
    }
    "Overwrite".slide()
}
