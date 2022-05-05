package drawings.examples.basics

import coracle.Drawing
import coracle.TWO_PI
import kotlin.math.cos
import kotlin.math.sin

class TextDrawing: Drawing() {

    var segmentCount = 200
    var step = 0
    var plotRadius = 0f

    override fun setup() {
        size(500, 450)
        noStroke()
        plotRadius  = (width * .75f)/2
    }

    override fun draw() {
        background(0xf5f2f0)
        if (step == segmentCount) step = -1
        step++
        val angle = (step * TWO_PI / segmentCount).toFloat()

        fill(0x9da7cb)

        text(
            text = "Hello",
            x = width/2 - 40 + (cos(angle) * plotRadius).toInt(),
            y = height/2 + (sin(angle) * plotRadius).toInt(),
            size = 30
        )

        fill(0xebb9ce)
        text("Coracle", 140, height/2, 55)
    }
}