package dsl.model

interface Tree<out T> {
    val data: T
    val nodes: List<Tree<T>>
}

fun <T> Tree(data: T, nodes: List<Tree<T>>): Tree<T> = TreeImpl(data, nodes)

data class TreeImpl<T>(
    override val data: T,
    override val nodes: List<Tree<T>>
) : Tree<T>
