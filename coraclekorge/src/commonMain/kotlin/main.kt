import com.soywiz.korge.*

suspend fun main() = Korge(
	width = 450,
	height = 450) {

	KorgeTriangleStripDrawing()
		.renderer(KorgeRenderer(this))
		.start()
}