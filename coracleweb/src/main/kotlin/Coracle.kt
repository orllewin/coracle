import examples.basics.ColourLerpDrawing

fun main() {
    console.log("Coracle")

    ColourLerpDrawing()
        .renderer(WebRenderer("coracle_canvas"))
        .start()
}