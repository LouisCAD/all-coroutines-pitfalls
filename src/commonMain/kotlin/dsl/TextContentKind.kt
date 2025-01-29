package dsl

sealed interface TextContentKind {
    data object PresentationOpening : TextContentKind
    data object CenteredTitle : TextContentKind
    data object BigFact : TextContentKind
    data object NewSection : TextContentKind
}
