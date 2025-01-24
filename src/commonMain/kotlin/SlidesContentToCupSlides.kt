import dsl.*
import net.kodein.cup.Slide

//TODO: When on the last step of a slide, show a hint (e.g. a camera emoji or icon),
// to let people know it's time to take a picture before the content goes away.

//TODO: Do a chapter closing

fun slides(
    builder: SlidesBuilder.() -> Unit
): List<Slide> {
    return CupSlidesBuilder().apply(builder).toSlides()
}

private class CupSlidesBuilder : SlidesBuilder() {

    fun toSlides(): List<Slide> {
        TODO()
    }

    private val tree: Nothing = TODO()
    override fun slide(title: String, contentKind: TextContentKind, subtitle: String?) {
        TODO()
    }

    override fun slide(
        title: String?,
        disposition: Disposition,
        startCentered: Boolean,
        block: SlideBuilder.() -> Unit
    ) {
        TODO()
    }

    override fun slidesGroup(
        title: String,
        disposition: Disposition,
        smallTitle: String?,
        subtitle: String?,
        slideContent: SlideBuilder.() -> Unit,
        groupContent: SlidesBuilder.() -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun comparison(block: SlidesBuilder.() -> Unit) {
        TODO()
    }
}

private class SlideItem(
    val parentSlidesTitles: List<SlideTitle>
)

private class SlideTitle(
    val text: String,
    val smallTitle: String?
)

private class SlideContentItem(
    val text: String,
    val sideLabel: String?,
)

private class Tree<T>(
    val data: T,
    val nodes: List<Tree<T>>
)


private sealed interface SlidesTreeBuilder {
    class Branch() : SlidesTreeBuilder

    class Leaf() : SlidesTreeBuilder
}

private sealed class TitledTree<LeafT>() {
    data class Branch<LeafT>(
        val title: String,
        val metaData: Color? = null,
        val nodes: List<TitledTree<LeafT>>
    ) : TitledTree<LeafT>() {

        companion object
    }

    data class Leaf<LeafT>(
        val data: LeafT
    ) : TitledTree<LeafT>()
}
