import  examples.experiments.CirclePackingDrawing

fun main() {
    CirclePackingDrawing()
        .renderer(JVMRenderer())
        .start()
}