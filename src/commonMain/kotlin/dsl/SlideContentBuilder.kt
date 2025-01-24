package dsl

interface SlideContentBuilder {

    operator fun String.invoke(
        sideLabel: String? = null,
        block: SlideContentBuilder.() -> Unit = {}
    )
}
