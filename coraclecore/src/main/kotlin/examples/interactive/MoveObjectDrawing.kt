package examples.interactive

import coracle.CoracleEventListener
import coracle.Drawing

/*
    A port of: https://processing.org/examples/mousefunctions.html
 */
class MoveObjectDrawing: Drawing() {

    var bx = 0f
    var by = 0f
    var boxSize = 150
    var xOffset = 0.0f
    var yOffset = 0.0f
    var holding = false

    override fun setup() {
        size(500, 400)

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
        background(0x1d1d1d)

        when {
            holding || overBox() -> fill(0xaf88e5)
            else -> fill(0x666666)
        }

        if(holding){
            bx = mouseX - xOffset
            by = mouseY - yOffset
        }

        rect(bx.toInt(), by.toInt(), boxSize, boxSize)
    }

    private fun overBox(): Boolean = mouseX > bx && mouseX < bx+boxSize && mouseY > by && mouseY < by + boxSize
}