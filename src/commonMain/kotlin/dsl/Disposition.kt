package dsl

sealed interface Disposition {
    sealed interface Bullets : Disposition {
        companion object : Bullets
        data object Numbers : Bullets
        data object Abc : Bullets
    }
    sealed interface List : Disposition {
        data object Slotted : List
        companion object : List
    }
    // Lists with different spacings/styles
    // Cards
}
