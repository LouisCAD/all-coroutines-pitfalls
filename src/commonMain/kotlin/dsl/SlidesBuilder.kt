package dsl

abstract class SlidesBuilder : SubSlidesBuilder() {

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
        titleOnlyForOverview: Boolean = false,
        block: SubSlidesBuilder.() -> Unit
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
}
