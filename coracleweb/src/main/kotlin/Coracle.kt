import examples.basics.ShapeDrawing
import examples.interactive.ConstrainDrawing

fun main() {
    ShapeDrawing()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}