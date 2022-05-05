package drawings.reference

import coracle.Drawing
import coracle.random
import coracle.shapes.Line
import coracle.shapes.Point

class ReferenceLine: Drawing() {

    private val worldColour = 0xf5f2f0
    private val lineColour = 0x000000

    private lateinit var lines: Array<WalkingLine>

    override fun setup() {
        size(450, 450)
        fill(lineColour, 0.4f)

        lines = Array(8){ WalkingLine() }
    }

    override fun draw() {
        background(worldColour)

        lines.forEach { line ->
            line
                .move()
                .drawLine()
                .drawIntersects()
        }
    }

    inner class WalkingLine {

        val a = EndPoint()
        val b = EndPoint()
        val line = Line()

        fun move(): WalkingLine{
            a.move()
            b.move()

            line.update(a, b)

            return this
        }


        fun drawLine(): WalkingLine{
            stroke(lineColour)
            line.draw()
            return this
        }

        fun drawIntersects(): WalkingLine {
            lines.forEach { other ->
                if(other != this){
                    val intersect = line.intersects(other.line)

                    intersect?.let{
                        noStroke()
                        circle(intersect, 5)
                    }
                }
            }

            return this
        }
    }

    inner class EndPoint: Point() {

        private var xDirection = 1
        private var yDirection = 1

        init {
            x = random(width)
            y = random(height)

            xDirection = when {
                random(100) > 50 -> 1
                else -> -1
            }

            yDirection = when {
                random(100) > 50 -> 1
                else -> -1
            }
        }

        fun move(){
            when {
                x > width || x < 0 -> xDirection *= -1
            }
            when {
                y > height || y < 0 -> yDirection *= -1
            }

            x += (2 * xDirection)
            y += (2 * yDirection)
        }
    }
}