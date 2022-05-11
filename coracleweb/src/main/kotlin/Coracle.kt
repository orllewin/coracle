import drawings.experiments.rad.RadImageConverter

fun main() {

    RadImageConverter()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}