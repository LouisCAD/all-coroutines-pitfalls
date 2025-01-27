package dsl.builder

import dsl.SlideBuilder
import dsl.model.SlideContentItem
import dsl.model.Tree

class SlideContentItemBuilder(
    private val trees: MutableList<Tree<SlideContentItem>> = mutableListOf()
) : SlideBuilder {

    override fun String.invoke(
        sideLabel: String?,
        block: SlideBuilder.() -> Unit
    ) {
        val newList = mutableListOf<Tree<SlideContentItem>>()
        trees += Tree(
            data = SlideContentItem(
                text = this,
                sideLabel = sideLabel
            ),
            nodes = newList
        )
        SlideContentItemBuilder(newList).block()
    }
}
