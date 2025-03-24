package content

import dsl.Disposition.Bullets
import dsl.SlidesBuilder

fun SlidesBuilder.pitfallsInStdlibKotlinCoroutinesPackage(title: String) = title.slidesGroup(
    disposition = Bullets.Numbers,
    smallTitle = ""
) {

}
