import androidx.collection.MutableIntList
import dsl.SideBySideDelivery
import dsl.model.SlideData

class SideBySideStepRetriever(data: SlideData.SideBySide) {
    fun indexForColumn(
        delivery: SideBySideDelivery,
        columnIndex: Int,
        globalStep: Int
    ): Int {
        if (globalStep < 0) return -1
        return when (delivery) {
            SideBySideDelivery.PageByPage -> globalStep - stepCounts.take(columnIndex).sum()
            SideBySideDelivery.PerLine -> columnLocalSteps[columnIndex][globalStep]
        }
    }

    private val stepCounts: List<Int> = data.slides.map { it.content.stepsCount() }
    val stepCount = stepCounts.sum()
    private val columnLocalSteps: List<MutableIntList> = data.slides.map { MutableIntList(initialCapacity = stepCount) }

    init {
        var lineIndex = 0
        var targetColumnIndex = 0
        val columnCount = data.slides.size
        fun nextCell() {
            if (targetColumnIndex + 1 == columnCount) {
                targetColumnIndex = 0
                lineIndex++
            } else targetColumnIndex++
        }
        for (globalStep in 0 until stepCount) {
            run updateCell@{
                repeat(columnCount) {
                    val hasLineForCurrentStep = lineIndex < stepCounts[targetColumnIndex]
                    if (hasLineForCurrentStep) {
                        return@updateCell
                    } else {
                        nextCell()
                    }
                }
                error("WTF? We should have found a column")
            }
            for (columnIndex in 0..<columnCount) {
                val localSteps = columnLocalSteps[columnIndex]
                localSteps += if (columnIndex == targetColumnIndex) {
                    lineIndex
                } else {
                    val lastLineIndexForColumn = if (localSteps.isEmpty()) -1 else localSteps.last()
                    lastLineIndexForColumn
                }
            }
            nextCell()
        }
    }
}