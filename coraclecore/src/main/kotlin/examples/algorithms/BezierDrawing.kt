package examples.algorithms

import coracle.Colour
import coracle.Drawing
import coracle.shapes.Bezier
import coracle.shapes.Point

class BezierDrawing: Drawing() {

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        background(0xeeeae4)

        val colourA = Colour(0x9da7cb)
        val colourB= Colour(0xcbb0cc)

        repeat(100){ i ->
            val bezierA = Bezier(
                Point(30, height/4),
                Point(100, i * 5),
                Point(width - 100, i * 5),
                Point(width - 30, height/4),
                100
            )

            val c = Colour.lerp(colourA, colourB, i/50f)
            stroke(c)
            bezierA.lines.forEach { line ->
                line(line)
            }
        }

        noLoop()
    }
}