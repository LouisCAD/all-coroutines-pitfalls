import dsl.model.SlideContent
import dsl.model.SlideContentItem
import dsl.model.SlideData
import dsl.model.Tree

fun SlideData.slideName(index: Int): String = currentTitle?.smallTitle ?: index.toString()

fun SlideContent.stepsCount(): Int = when (this) {
    is SlideContent.Elements -> this.elements.sumOf { it.countSteps() }
    is SlideContent.SingleElement -> 0
}

fun Tree<SlideContentItem>.countSteps(): Int {
    return 1 + (if (data.sideLabel != null) 1 else 0) + nodes.sumOf { it.countSteps() }
}
