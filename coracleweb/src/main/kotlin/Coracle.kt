import drawings.experiments.boids.tadpoles.TadpolesTails

fun main() {

    TadpolesTails()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}