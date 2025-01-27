package dsl.model

interface Tree<out T> {
    val data: T
    val nodes: List<Tree<T>>
}

fun <T> Tree(data: T, nodes: List<Tree<T>>): Tree<T> = object : Tree<T> {
    override val data: T = data
    override val nodes: List<Tree<T>> = nodes
}
