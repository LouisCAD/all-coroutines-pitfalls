package dsl

import kotlin.jvm.JvmName

abstract class SlidesBuilder : SubSlidesBuilder() {

    fun String.slidesGroup(
        disposition: TextContentKind = TextContentKind.NewSection,
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
        disposition: Disposition,
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

    @JvmName("sideBySideWithReceiver")
    fun String.sideBySide(
        subtitle: String? = null,
        overviewTitle: String? = this,
        delivery: SideBySideDelivery = SideBySideDelivery.PageByPage,
        block: SubSlidesBuilder.() -> Unit
    ) = sideBySide(this, subtitle, overviewTitle, delivery, block)

    abstract fun sideBySide(
        title: String? = null,
        subtitle: String? = null,
        overviewTitle: String? = title,
        delivery: SideBySideDelivery = SideBySideDelivery.PageByPage,
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
        disposition: Disposition,
        smallTitle: String?,
        subtitle: String?,
        groupContent: SlidesBuilder.() -> Unit
    )
}
