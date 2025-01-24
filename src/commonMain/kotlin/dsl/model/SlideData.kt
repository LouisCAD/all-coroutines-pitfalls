package dsl.model

data class SlideDataOld(
    val parentTitles: List<SlideTitle>,
    val currentTitle: SlideTitle?,
    val content: SlideContent
)

sealed interface SlideData {
    data class Single(
        override val parentTitles: List<SlideTitle>,
        override val currentTitle: SlideTitle?,
        override val content: SlideContent
    ) : SlideData, WithParentTitles, TitleAndContent

    data class SubSlide(
        override val currentTitle: SlideTitle?,
        override val content: SlideContent
    ) : SlideData, TitleAndContent

    data class Comparison(
        override val parentTitles: List<SlideTitle>,
        val currentTitle: SlideTitle?,
        val slides: List<SubSlide>
    ) : SlideData, WithParentTitles

    sealed interface WithParentTitles : SlideData {
        val parentTitles: List<SlideTitle>
    }

    sealed interface TitleAndContent : SlideData {
        val currentTitle: SlideTitle?
        val content: SlideContent
    }
}
