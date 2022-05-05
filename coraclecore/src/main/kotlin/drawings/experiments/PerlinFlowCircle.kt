package drawings.experiments

import coracle.*
import coracle.Math.map
import kotlin.math.cos
import kotlin.math.sin

class PerlinFlowCircle: Drawing() {
    override fun setup() {
        size(550, 550)
    }

    override fun draw() {
        background(0xffffff)
        stroke(0x000000, 0.3f)
        Perlin.newSeed()
        val scale = 0.006f

        val count = randomInt(100, 500)
        val r = randomInt(80, 160)
        repeat(count){ i ->
            val angle = (i * TWO_PI / count).toFloat()
            var x = (width/2) + cos(angle) * r
            var y = (height/2) + sin(angle) * r
            val length = randomInt(55, 400)
            repeat(length){
                val theta = map((Perlin.noise(x * scale, y * scale) * TAU).toFloat(), -1f, 1f, 0f, height.toFloat()/4f)
                x += cos(theta)
                y += sin(theta)
                point(x, y)
            }
        }
        noLoop()
    }
}