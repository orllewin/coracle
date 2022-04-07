package examples.interactive

import coracle.Drawing
import coracle.Math.constrain
import kotlin.math.abs

class ConstrainDrawing: Drawing() {

    var mx = 0f
    var my = 0f
    var easing = 0.05f
    var radius = 24
    var margin = 100
    var inner = margin + radius

    override fun setup() {
        size(450, 450)
        interactiveMode()
        noStroke()
    }

    override fun draw() {
        background(0x515151)

        when {
            abs(mouseX - mx) > 0.1 -> mx += (mouseX - mx) * easing
        }
        when {
            abs(mouseY - my) > 0.1 -> my += (mouseY - my) * easing
        }

        mx = constrain(mx, inner.toFloat(), (width - inner).toFloat())
        my = constrain(my, inner.toFloat(), (height - inner).toFloat())
        fill(0x767676)
        rect(margin, margin, width - (margin*2), height - (margin*2))
        fill(0xffffff)
        circle(mx, my, radius)
    }
}