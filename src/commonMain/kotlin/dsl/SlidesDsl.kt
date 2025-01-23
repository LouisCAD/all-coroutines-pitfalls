package dsl

val defaultDisposition: Disposition = Disposition.List
val defaultTextContentKind: TextContentKind = TextContentKind.BigFact //TODO

interface SlidesBuilder {

    fun String.slide(
        whatever: TextContentKind = defaultTextContentKind,
        subtitle: String? = null
    )

    fun slide(
        disposition: Disposition = defaultDisposition,
        block: SlideBuilder.() -> Unit
    )

    fun String.slide(
        disposition: Disposition = defaultDisposition,
        block: SlideBuilder.() -> Unit
    )

    fun String.slidesGroup(
        disposition: Disposition = defaultDisposition,
        smallTitle: String = this,
        block: SlideGroup.() -> Unit
    )

    fun comparison(
        block: SlidesBuilder.() -> Unit
    )
}

interface SlideGroup : SlidesBuilder, SlideBuilder

interface SlideBuilder : SlideContentBuilder {
    fun subtitle(text: String)
}

interface SlideContentBuilder {

    operator fun String.invoke(
        sideLabel: String? = null,
        block: SlideContentBuilder.() -> Unit = {}
    )

}
