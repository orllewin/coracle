package reference

import coracle.Color
import coracle.Drawing
import coracle.randomInt
import coracle.shapes.Bezier
import coracle.shapes.Point

class ReferenceBezier: Drawing() {

    override fun setup() = size(450, 450)

    override fun draw() {
        background(0xEFEDF5)

        val anchor1 = Point(randomInt(20, width-20), randomInt(20, height-20))
        val control1 = Point(randomInt(20, width-20), randomInt(20, height-20))
        val control2 = Point(randomInt(20, width-20), randomInt(20, height-20))
        val anchor2 = Point(randomInt(20, width-20), randomInt(20, height-20))

        val bezier = Bezier(anchor1, control1, control2, anchor2)

        //Draw the curve
        stroke(0x1dd1d1)
        //bezier.draw()

        val colourA = Color.random()
        val colourB = Color.random()
        bezier.lines.forEachIndexed { index, line ->
            stroke(Color.lerp(colourA, colourB, (index.toFloat()/bezier.lines.size)))
            line(line)
        }

        //Draw the control points
        stroke(0x886644, 0.8f)
        line(anchor1.x, anchor1.y, control1.x, control1.y)
        line(anchor2.x, anchor2.y, control2.x, control2.y)

        fill(0x82789D)
        noStroke()
        circle(control1.x, control1.y, 5)
        circle(control2.x, control2.y, 5)

        noLoop()
    }
}