package examples.experiments

import coracle.Colour
import coracle.Drawing
import coracle.random
import coracle.shapes.CatmullRomSpline
import kotlin.math.cos
import kotlin.math.sin

class CatmulRomDrawing: Drawing() {

    private val iterations = 692
    private var angle = 0.0
    private val xSeed = random(6f, 15f)
    private var xOrigin = 0f
    private val lineIncrement = 1.45f
    private val speed = 0.05f

    private val catmullRom = CatmullRomSpline(CatmullRomSpline.Type.CENTRIPETAL, 8)

    override fun setup() {
        size(875, 450)
    }

    override fun draw() {
        background(0xeeeae4)

        val margin = 130
        repeat(iterations) { index ->
            val x = cos(angle / xSeed).toFloat() * 100f
            val y = (height / 2) + sin(angle) * 200
            val yAnchor = (height / 2) + 40 * sin((index) * 3.14 / 180)
            val xAnchor = 25 * sin((index) * 3.14 / 180)
            catmullRom.addVertex(xOrigin + xAnchor.toFloat() - margin, yAnchor.toFloat())
            catmullRom.addVertex(x + xOrigin - margin, height / 2f)
            catmullRom.addVertex(x + xOrigin - margin, y.toFloat())
            xOrigin += lineIncrement
            angle += speed
        }

        catmullRom.build()

        val colourA = Colour(0x9da7cb)
        val colourB= Colour(0xcbb0cc)
        val points = catmullRom.points.size

        catmullRom.lines.forEachIndexed { i, line ->
            val c = Colour.lerp(colourA, colourB, (i.toFloat()/points.toFloat()))
            stroke(c)
            line(line)
        }

        noLoop()
    }
}