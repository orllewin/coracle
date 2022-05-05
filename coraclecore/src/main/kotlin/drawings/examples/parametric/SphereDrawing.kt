package drawings.examples.parametric

import coracle.Drawing
import kotlin.math.cos
import kotlin.math.sin

/*

    A port of some old BBC Micro BASIC code

 */
class SphereDrawing: Drawing() {

    var radius = 150
    var angle = 0f
    var lastX = 0f
    var lastY = 0f

    override fun setup() {
        size(350, 350)
        translate(width / 2, height / 2)

        background(DEFAULT_BACKGROUND)
        stroke(DEFAULT_FOREGROUND)
    }

    override fun draw() {
        val x: Float = radius * sin(angle)
        val y: Float = radius * cos(angle) * sin(angle * 0.95f)
        line(lastX, lastY, x, y)
        lastX = x
        lastY = y
        angle += 0.1.toFloat()

        if(angle > 126) noLoop()
    }
}