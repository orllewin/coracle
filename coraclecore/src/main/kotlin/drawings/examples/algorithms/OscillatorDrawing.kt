package drawings.examples.algorithms

import coracle.Drawing
import kotlin.math.sin

class OscillatorDrawing: Drawing() {

    var theta = 0.0f

    override fun setup() {
        size(450, 150)
        translate(width/2, height/2)
        noStroke()
        fill(0xbde4df)
    }

    override fun draw() {
        background(0xf5f2f0)

        theta += 0.02f
        circle(sin(theta) * 100f, 0, 30)
    }
}