package examples.experiments

import coracle.*
import coracle.Math.deg
import coracle.Math.randomDegree
import coracle.shapes.Line
import coracle.shapes.Point

class GrowthDrawing: Drawing() {

    private var reduction = 1.5f

    private val lines = mutableListOf<Line>()

    override fun setup() {
        size(550, 550)
    }

    override fun draw() {
        matchWidth()
        background(0xeeeae4)
        stroke(0x5b5767, 0.3f)

        val length = 100f
        reduction = random(1.45f, 1.55f)
        repeat(randomInt(4, 8)){
            grow(Point(width/2, height/2), length, randomDegree())
        }

        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE

        lines.forEach { line ->
            if(line.x1 < minX){
                minX = line.x1.toInt()
            }
            if(line.x2 < minX){
                minX = line.x2.toInt()
            }
            if(line.x1 > maxX){
                maxX = line.x1.toInt()
            }
            if(line.x2 > maxX){
                maxX = line.x2.toInt()
            }
        }

        //Should the drawing be stretched horizontally to fill desktop screens:
        val map = width in 800..3000

        when {
            map -> {
                val drawWidth = width * 0.55f
                val drawMargin = (width - drawWidth)/2f

                lines.forEach { line ->
                    val x1 = Math.map(line.x1, minX.toFloat(), maxX.toFloat(), drawMargin, width - drawMargin)
                    val x2 = Math.map(line.x2, minX.toFloat(), maxX.toFloat(), drawMargin, width - drawMargin)
                    line(x1, line.y1, x2, line.y2)
                }
            }
            else -> {
                lines.forEachIndexed { i, line ->
                    line(line.x1, line.y1, line.x2, line.y2)
                }
            }
        }

        noLoop()
    }

    private fun grow(p: Point, length: Float, angle: Degree){
        if (length < 2) return
        val p2 = p.plot(angle, length)
        lines.add(Line(p, p2))
        val i = randomInt(2, 4)
        repeat(i) {
            val a: Radian = random(-12f, 12f).toDouble()
            grow(p2, length / reduction, a.deg())
        }
    }
}