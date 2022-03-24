import examples.experiments.LifeDrawing2

fun main() {
    console.log("Coracle")

    LifeDrawing2()
        .renderer(WebRenderer("coracle_canvas"))
        .start()
}