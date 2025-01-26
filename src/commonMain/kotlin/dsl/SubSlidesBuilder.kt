package dsl

abstract class SubSlidesBuilder {

    fun String.slide(
        contentKind: TextContentKind = defaultTextContentKind,
        subtitle: String? = null
    ) = slideWithTextContent(
        title = this,
        contentKind = contentKind,
        subtitle = subtitle
    )

    fun slide(
        disposition: Disposition = defaultDisposition,
        block: SlideBuilder.() -> Unit
    ) = slideWithTextTree(
        title = null,
        subtitle = null,
        disposition = disposition,
        startCentered = false,
        block = block
    )

    fun String.slide(
        disposition: Disposition = defaultDisposition,
        subtitle: String? = null,
        startCentered: Boolean = false,
        block: SlideBuilder.() -> Unit
    ) = slideWithTextTree(
        title = this,
        disposition = disposition,
        subtitle = subtitle,
        startCentered = startCentered,
        block = block
    )

    protected abstract fun slideWithTextContent(
        title: String,
        contentKind: TextContentKind,
        subtitle: String? = null
    )

    protected abstract fun slideWithTextTree(
        title: String?,
        subtitle: String?,
        disposition: Disposition,
        startCentered: Boolean,
        block: SlideBuilder.() -> Unit
    )
}
