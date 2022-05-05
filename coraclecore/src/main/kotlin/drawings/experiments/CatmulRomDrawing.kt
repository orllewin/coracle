package drawings.experiments

import coracle.*
import coracle.shapes.CatmullRomSpline
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class CatmulRomDrawing: Drawing() {

    private val iterations = 692
    private var angle = 0.0
    private val xSeed = random(6f, 15f)
    private var xOrigin = 20f
    private var lineIncrement = 1.45f
    private val speed = 0.05f

    private val catmullRom = CatmullRomSpline(CatmullRomSpline.TYPE_CENTRIPETAL, 16)

    override fun setup() {
        size(875, 450)
    }

    override fun draw() {
        matchWidth()
        background(0xeeeae4)

        lineIncrement = max((width/iterations).toFloat(), lineIncrement)

        repeat(iterations) { index ->

            //Sine 1 - middle guideline 1
            val sineAX = xOrigin
            val sineAY = (sin(index * PI/180) * 40) + height/2
            catmullRom.addVertex(sineAX, sineAY.toFloat())

            //Sine 2 - middle guideline 2
            val sineBX = xOrigin + cos(angle / xSeed).toFloat() * 190f
            val sineBY = height/2 - (40 * sin(index * PI/180))
            catmullRom.addVertex(sineBX, sineBY.toFloat())

            //Sine 3 - outlier
            val sineCX = sineBX
            val sineCY = (height / 2) + sin(angle) * 200
            catmullRom.addVertex(sineCX, sineCY.toFloat())

            xOrigin += lineIncrement
            angle += speed
        }

        catmullRom.build()

        var xMin = Int.MAX_VALUE
        var xMax = Int.MIN_VALUE

        catmullRom.points.forEach { p ->
            if(p.x < xMin) xMin = p.x.toInt()
            if(p.x > xMax) xMax = p.x.toInt()
        }

        val colourA = Colour(0x2E346F)
        val colourB= Colour(0x9D5E9F)
        val points = catmullRom.points.size

        catmullRom.lines.forEachIndexed { i, line ->
            val c = Colour.lerp(colourA, colourB, (i.toFloat()/points.toFloat()))
            stroke(c, 0.5f)
            line(mapX(line.x1, xMin.toFloat(), xMax.toFloat()), line.y1, mapX(line.x2, xMin.toFloat(), xMax.toFloat()), line.y2)
        }

        noLoop()
    }

    private fun mapX(x: Float, min: Float, max: Float): Float = Math.map(x, min, max, 20f, width - 20f)
}