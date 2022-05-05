package drawings.experiments

import coracle.*
import coracle.Math.map
import coracle.PI
import kotlin.math.*

/*
    Adapted from: https://observablehq.com/@mootari/polygon-edge-distance
 */
class PerlinFlowTriangle: Drawing() {

    val sides = 5
    val density = 20

    val vertextLengths = IntArray(sides * density){  randomInt(55, 400) }
    val radius = width/4

    var angleOffset = when (sides) {
        3 -> 0.25 * TAU / sides
        4 -> -0.5 * TAU / sides
        5 -> -0.25 * TAU / sides
        else -> 0.0
    }

    var angleOffsetA = angleOffset
    var angleOffsetB = angleOffset

    val scale = 0.006f

    override fun setup() {
        size(550, 550)
        Perlin.newSeed()
        stroke(0xffffff, 0.008f)
        background(0x000000)
    }

    override fun draw() {
        //background(0xffffff)


        angleOffsetA += 0.05f
        angleOffsetB += 0.13f
        drawCycle(angleOffsetA, 0)
        drawCycle(angleOffsetB, -width/2)

        //foreground(0xffffff, 0.15f)
    }

    fun drawCycle(aOffset: Double, xoffset: Int){
        val length = sides * density
        repeat(sides * density){ i ->
            val offset = (i.toDouble()/length)
            val angle = offset * TAU
            val r = radius * edgeDistance(sides, angle, aOffset)
            var x =   r * cos(angle) * aOffset/10
            var y = (height/2) + r * sin(angle) * aOffset/10

            repeat(vertextLengths[i]){
                val theta = map((Perlin.noise(x * scale, y * scale) * TAU).toFloat(), -1f, 1f, 0f, height.toFloat()/4f)
                x += cos(theta) + (aOffset/10) + xoffset
                y += sin(theta)
                point(x, y)
            }

        }
    }

    fun edgeDistance(count: Int, angle: Double, offset: Double = 0.0): Double {
        return cos(PI / count) / cos(PI / count * tri(.5 + .5 * count / PI * (angle - offset)))
    }

    fun tri(t: Double): Double {
        return abs(t - floor(t + .5)) * 2;
    }
}