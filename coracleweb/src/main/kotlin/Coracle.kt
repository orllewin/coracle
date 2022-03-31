import examples.basics.HelloCoracleDrawing
import examples.basics.TextDrawing

fun main() {
    console.log("Coracle")

    TextDrawing()
        .renderer(WebRenderer("coracle_canvas"))
        .start()
}