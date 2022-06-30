import drawings.experiments.boids.OrllewinDrawing

fun main() {

    OrllewinDrawing()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}