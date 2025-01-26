package dsl.model

interface MutableTree<T> : Tree<T> {
    override var data: T
    override val nodes: MutableList<MutableTree<T>>
}
