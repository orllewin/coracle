package examples.basics

import coracle.Coord
import coracle.Drawing

/*
    This is experimental and involves object allocations within the draw loop.
    Allocation in the draw loop will impact performance and should be avoided as much as possible.
 */
class HelloInfixDrawing: Drawing() {

    override fun setup() {
        size(300, 300)

    }

    override fun draw() {
        background(0xffcdcd)

        noStroke()
        Circle(20) at Coord(width/2, height/2)
        Square(80) at Coord(width/4, height/4)
    }
}