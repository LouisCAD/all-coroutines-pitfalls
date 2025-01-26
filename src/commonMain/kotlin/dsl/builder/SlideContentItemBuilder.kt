package dsl.builder

import dsl.SlideBuilder
import dsl.model.MutableTree
import dsl.model.SlideContentItem

class SlideContentItemBuilder(
    private val trees: MutableList<MutableTree<SlideContentItem>> = mutableListOf()
) : SlideBuilder {

    override fun String.invoke(
        sideLabel: String?,
        block: SlideBuilder.() -> Unit
    ) {
        val newList = mutableListOf<MutableTree<SlideContentItem>>()
        trees += MutableTreeImpl(
            data = SlideContentItem(
                text = this,
                sideLabel = sideLabel
            ),
            nodes = newList
        )
        SlideContentItemBuilder(newList).block()
    }
}

private class MutableTreeImpl<T>(
    override var data: T,
    override val nodes: MutableList<MutableTree<T>>
) : MutableTree<T>
