import dsl.model.SlideData
import net.kodein.cup.Slide

interface CupSlidesMaker {

    /**
     * This function works on all slides at once, instead of a per-slide basis,
     * so implementations can leverage the context of previous and next slide
     * data elements to create the final set of CuP slides.
     */
    fun buildSlides(data: List<SlideData.TopLevel>): List<Slide>
}
