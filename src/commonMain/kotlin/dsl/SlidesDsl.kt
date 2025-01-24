package dsl

val defaultDisposition: Disposition = Disposition.List
val defaultTextContentKind: TextContentKind = TextContentKind.BigFact //TODO

abstract class SlidesBuilder {

    fun String.slide(
        contentKind: TextContentKind = defaultTextContentKind,
        subtitle: String? = null
    ) = slide(title = this, contentKind = contentKind, subtitle = subtitle)

    fun slide(
        disposition: Disposition = defaultDisposition,
        block: SlideBuilder.() -> Unit
    ) = slide(title = null, disposition = disposition, block = block)

    fun String.slide(
        disposition: Disposition = defaultDisposition,
        startCentered: Boolean = false,
        block: SlideBuilder.() -> Unit
    ) = slide(title = this, disposition = disposition, startCentered = startCentered, block = block)

    fun String.slidesGroup(
        disposition: Disposition = defaultDisposition,
        smallTitle: String? = null,
        subtitle: String? = null,
        slideContent: SlideBuilder.() -> Unit,
        groupContent: SlidesBuilder.() -> Unit
    ) = slidesGroup(
        title = this,
        disposition = disposition,
        subtitle = subtitle,
        smallTitle = smallTitle,
        slideContent = slideContent,
        groupContent = groupContent
    )

    abstract fun comparison(
        block: SlidesBuilder.() -> Unit
    )

    //region Implementation details

    protected abstract fun slide(
        title: String,
        contentKind: TextContentKind,
        subtitle: String? = null
    )

    protected abstract fun slide(
        title: String?,
        disposition: Disposition,
        startCentered: Boolean = false,
        block: SlideBuilder.() -> Unit
    )

    protected abstract fun slidesGroup(
        title: String,
        disposition: Disposition,
        smallTitle: String?,
        subtitle: String?,
        slideContent: SlideBuilder.() -> Unit,
        groupContent: SlidesBuilder.() -> Unit
    )

    //endregion
}

interface SlideBuilder : SlideContentBuilder {
    fun subtitle(text: String)
}

interface SlideContentBuilder {

    operator fun String.invoke(
        sideLabel: String? = null,
        block: SlideContentBuilder.() -> Unit = {}
    )

}
