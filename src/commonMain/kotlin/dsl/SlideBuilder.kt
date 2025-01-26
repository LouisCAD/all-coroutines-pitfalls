package dsl

interface SlideBuilder {

    operator fun String.invoke(
        sideLabel: String? = null,
        block: SlideBuilder.() -> Unit = {}
    )
}
