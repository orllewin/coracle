package drawings.experiments

import coracle.Colour
import coracle.Drawing
import kotlin.math.sqrt

/*
    Adapted from: https://thecodingtrain.com/CodingChallenges/021-mandelbrot-p5.html
 */
class Mandelbrot: Drawing() {

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        val w = 5f
        val h: Float = w * height / width

        val xmin = -w / 2f
        val ymin = -h / 2f
        val maxiterations = 100

        val xmax = xmin + w
        val ymax = ymin + h

        val dx = (xmax - xmin) / width
        val dy = (ymax - ymin) / height

        var y = ymin

        repeat(height){ j ->
            var x = xmin

            repeat(width){ i ->
                var a = x
                var b = y
                var n = 0

                while (n < maxiterations) {
                    val aa = a * a
                    val bb = b * b
                    val twoab = (2.0 * a * b).toFloat()
                    a = aa - bb + x
                    b = twoab + y
                    if (a * a + b * b > 16.0) {
                        break // Bail
                    }
                    n++
                }

                when (n) {
                    maxiterations -> {
                        stroke(0x000000)
                        point(i, j)
                    }
                    else -> {
                        val c = sqrt(n.toFloat() / maxiterations)
                        stroke(Colour.grey((c * 255).toInt()))
                        point(i, j)
                    }
                }
                x += dx
            }
            y += dy
        }

        noLoop()

    }
}