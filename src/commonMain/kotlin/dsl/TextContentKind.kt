package dsl

sealed interface TextContentKind {
    data object PresentationOpening : TextContentKind
    data object BigFact : TextContentKind
}
