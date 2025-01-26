package dsl.model

import dsl.Disposition
import dsl.TextContentKind

sealed interface SlideContent {

    data class Elements(
        val disposition: Disposition,
        val elements: List<Tree<SlideContentItem>>
    ) : SlideContent

    data class SingleElement(
        val contentKind: TextContentKind
    ) : SlideContent
}
