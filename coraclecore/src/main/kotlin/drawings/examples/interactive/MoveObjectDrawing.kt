package drawings.examples.interactive

import coracle.CoracleEventListener
import coracle.Drawing

/*
    A port of: https://processing.org/examples/mousefunctions.html
 */
class MoveObjectDrawing: Drawing() {

    var bx = 0f
    var by = 0f
    var boxSize = 100
    var xOffset = 0.0f
    var yOffset = 0.0f
    var holding = false

    override fun setup() {
        size(450, 450)

        bx = width/2f - boxSize/2f
        by = height/2f - boxSize/2f

        noStroke()

        interactiveMode(object : CoracleEventListener{
            override fun mousePressed() {
                when {
                    overBox() -> {
                        holding = true
                        xOffset = mouseX - bx
                        yOffset = mouseY - by
                    }
                }
            }

            override fun mouseReleased() {
                holding = false
            }
        })
    }

    override fun draw() {
        background(0xf5f2f0)

        stroke(0x000000)
        noFill()
        rect(0, 0, width, height)

        when {
            holding || overBox() -> fill(0xff7676)
            else -> fill(0xffffff)
        }

        if(holding){
            bx = mouseX - xOffset
            by = mouseY - yOffset
        }

        noStroke()
        rect(bx.toInt(), by.toInt(), boxSize, boxSize)
    }

    private fun overBox(): Boolean = mouseX > bx && mouseX < bx+boxSize && mouseY > by && mouseY < by + boxSize
}