import reference.ReferenceBezier

fun main() {
    ReferenceBezier()
        .renderer(WebRenderer("coracle_canvas", "out_area"))
        .start()
}