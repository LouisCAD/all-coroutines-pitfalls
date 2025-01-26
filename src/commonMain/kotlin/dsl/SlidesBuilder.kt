package dsl

abstract class SlidesBuilder {

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

    fun String.slidesGroup(
        disposition: TextContentKind,
        smallTitle: String? = null,
        subtitle: String? = null,
        groupContent: SlidesBuilder.() -> Unit
    ) = slidesGroupWithTextContent(
        title = this,
        contentKind = disposition,
        subtitle = subtitle,
        smallTitle = smallTitle,
        groupContent = groupContent
    )

    fun String.slidesGroup(
        disposition: Disposition? = null,
        smallTitle: String? = null,
        subtitle: String? = null,
        groupContent: SlidesBuilder.() -> Unit
    ) = slidesGroupWithTextTree(
        title = this,
        disposition = disposition,
        subtitle = subtitle,
        smallTitle = smallTitle,
        groupContent = groupContent
    )

    abstract fun comparison(
        title: String? = null,
        subtitle: String? = null,
        block: SlidesBuilder.() -> Unit
    )

    //region Implementation details ----------------------------------------

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

    protected abstract fun slidesGroupWithTextContent(
        title: String,
        contentKind: TextContentKind,
        smallTitle: String?,
        subtitle: String?,
        groupContent: SlidesBuilder.() -> Unit
    )

    protected abstract fun slidesGroupWithTextTree(
        title: String,
        disposition: Disposition?,
        smallTitle: String?,
        subtitle: String?,
        groupContent: SlidesBuilder.() -> Unit
    )

    //endregion
}
