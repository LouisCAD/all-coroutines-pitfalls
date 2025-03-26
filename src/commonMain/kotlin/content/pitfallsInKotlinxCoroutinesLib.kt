package content

import content.pitfalls.asyncAwaitAreDifferent
import content.pitfalls.breakingStructuredConcurrency
import content.pitfalls.eatingCancellationException
import dsl.Disposition.Bullets
import dsl.SlidesBuilder

fun SlidesBuilder.pitfallsInKotlinxCoroutinesLib(title: String) = title.slidesGroup(
    disposition = Bullets.Numbers,
    smallTitle = ""
) {
    eatingCancellationException()
    asyncAwaitAreDifferent()
    breakingStructuredConcurrency()
}
