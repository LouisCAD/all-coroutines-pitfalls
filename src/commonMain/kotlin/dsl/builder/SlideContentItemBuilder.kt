package dsl.builder

import dsl.Disposition
import dsl.SlideBuilder
import dsl.model.SlideContentItem
import dsl.model.Tree

class SlideContentItemBuilder(
    private val trees: MutableList<Tree<SlideContentItem>> = mutableListOf()
) : SlideBuilder {

    override fun String.invoke(
        disposition: Disposition?,
        sideLabel: String?,
        block: SlideBuilder.() -> Unit
    ) {
        val newList = mutableListOf<Tree<SlideContentItem>>()
        trees += Tree(
            data = SlideContentItem(
                text = this,
                disposition = disposition,
                sideLabel = sideLabel
            ),
            nodes = newList
        )
        SlideContentItemBuilder(trees = newList).block()
    }
}
