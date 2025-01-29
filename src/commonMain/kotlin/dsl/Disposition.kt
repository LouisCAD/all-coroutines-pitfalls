package dsl

sealed interface Disposition {
    sealed interface Bullets : Disposition {
        companion object : Bullets
        data object Numbers : Bullets
        data object Abc : Bullets
        data object Arrow : Bullets
        data object Invisible : Bullets
    }
    sealed interface List : Disposition {
        data object Slotted : List
        companion object : List
        data object BigCentered : List
    }
    // Lists with different spacings/styles
    // Cards
}
