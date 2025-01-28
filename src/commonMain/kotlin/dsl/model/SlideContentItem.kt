package dsl.model

import dsl.Disposition

data class SlideContentItem(
    val text: String,
    val disposition: Disposition?,
    val sideLabel: String?,
)
