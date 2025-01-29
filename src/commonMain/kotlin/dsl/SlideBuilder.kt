package dsl

interface SlideBuilder {

    operator fun String.invoke(
        disposition: Disposition? = null,
        sideLabel: String? = null,
        block: SlideBuilder.() -> Unit = {}
    )
}
