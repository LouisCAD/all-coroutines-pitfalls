package dsl.model

interface Tree<out T> {
    val data: T
    val nodes: List<Tree<T>>
}
