package dsl.model

sealed interface SlideData {
    val currentTitle: SlideTitle?

    data class Single(
        override val parentTitles: List<SlideTitle>,
        override val currentTitle: SlideTitle?,
        override val content: SlideContent
    ) : SlideData, TopLevel, WithParentTitles, TitleAndContent

    data class SubSlide(
        override val currentTitle: SlideTitle?,
        override val content: SlideContent
    ) : SlideData, TitleAndContent

    data class Comparison(
        override val parentTitles: List<SlideTitle>,
        override val currentTitle: SlideTitle?,
        val slides: List<SubSlide>
    ) : SlideData, TopLevel, WithParentTitles

    sealed interface WithParentTitles : SlideData {
        val parentTitles: List<SlideTitle>
    }

    sealed interface TitleAndContent : SlideData {
        override val currentTitle: SlideTitle?
        val content: SlideContent
    }

    sealed interface TopLevel : SlideData
}
