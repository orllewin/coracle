import examples.experiments.CatmulRomDrawing

fun main() {
    CatmulRomDrawing()
        .renderer(JVMRenderer())
        .start()
}