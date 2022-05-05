package drawings.examples.interactive

import coracle.CoracleEventListener
import coracle.Drawing
import coracle.shapes.Circle

class InteractiveDrawing: Drawing() {

    var circles = mutableListOf<Circle>()

    override fun setup() {
        size(450, 450)

        //Listener interface is optional, to just use mouseX and mouseY use: interactiveMode()
        interactiveMode(object: CoracleEventListener {
            override fun mousePressed() { circles.add(Circle(mouseX, mouseY, 20)) }
            override fun mouseReleased() = Unit//NOOP
        })
    }

    override fun draw() {
        background(0xf5f2f0)

        //Draw border

        stroke(0x000000)
        noFill()
        rect(0, 0, width, height)
        line(0, mouseY, width, mouseY)
        line(mouseX, 0, mouseX, height)

        noStroke()
        fill(0x66eedd)
        circles.forEach { c -> c.draw() }
    }
}