package dsl.model

class Tree<T>(
    val data: T,
    val nodes: List<Tree<T>>
)
