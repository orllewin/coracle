import examples.experiments.CatmulRomDrawing

fun main() {
    console.log("Coracle")

    CatmulRomDrawing()
        .renderer(WebRenderer("coracle_canvas"))
        .start()
}