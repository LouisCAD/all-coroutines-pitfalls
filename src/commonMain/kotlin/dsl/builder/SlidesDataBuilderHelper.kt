@file:Suppress("UnusedReceiverParameter") // receiver here to clarify usage scope.

package dsl.builder

import dsl.Disposition
import dsl.SlideBuilder
import dsl.SubSlidesBuilder
import dsl.model.SlideContent
import dsl.model.SlideContentItem
import dsl.model.SlideTitle
import dsl.model.Tree

fun SubSlidesBuilder.slideTitleOrNull(
    title: String?,
    smallTitle: String? = null,
    subtitle: String?,
): SlideTitle? {
    return SlideTitle(
        text = title ?: return null,
        smallTitle = smallTitle,
        subtitle = subtitle
    )
}

fun SubSlidesBuilder.slideContentElements(
    disposition: Disposition,
    block: SlideBuilder.() -> Unit
): SlideContent.Elements = SlideContent.Elements(
    disposition = disposition,
    elements = mutableListOf<Tree<SlideContentItem>>().also { trees ->
        SlideContentItemBuilder(trees = trees).block()
    }
)
