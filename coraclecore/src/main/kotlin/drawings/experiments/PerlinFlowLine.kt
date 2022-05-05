package drawings.experiments

import coracle.*
import coracle.Math.map
import kotlin.math.cos
import kotlin.math.sin

class PerlinFlowLine: Drawing() {
    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        matchWidth()
        background(0xffffff)
        stroke(0x000000, 0.3f)
        Perlin.newSeed()
        val scale = 0.006f
        repeat(randomInt(100, 500)){
            var x = random(width)
            var y = height/2f

            val length = randomInt(55, 400)
            repeat(length){
                val theta = map((Perlin.noise(x * scale, y * scale) * (2 * PI)).toFloat(), -1f, 1f, 0f, height.toFloat()/4f)
                x += cos(theta)
                y += sin(theta)
                point(x, y)
            }
        }
        noLoop()
    }
}