import examples.experiments.CatmulRomSVGDrawing

fun main() {
    CatmulRomSVGDrawing()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}