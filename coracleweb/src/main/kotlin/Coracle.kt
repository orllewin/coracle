import examples.algorithms.SpatialHashCirclePacking
import examples.experiments.CatmulRomSVGDrawing
import examples.experiments.SpatialHashCirclePackingGrowth

fun main() {
    SpatialHashCirclePackingGrowth()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}