import drawings.experiments.boids.tadpoles.TadpolesTailsDark

fun main() {

    TadpolesTailsDark()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}