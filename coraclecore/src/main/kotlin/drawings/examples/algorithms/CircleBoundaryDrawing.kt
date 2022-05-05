package drawings.examples.algorithms

import coracle.Drawing
import coracle.Math
import coracle.TWO_PI
import coracle.random
import coracle.shapes.Circle
import coracle.shapes.Point
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/*
    Calculate if a point is inside a circle
 */
class CircleBoundaryDrawing: Drawing() {

    lateinit var boundary: Boundary

    val cells = mutableListOf<Cell>()
    val cellRadius = 12

    override fun setup() {
        size(450, 450)

        boundary = Boundary(width/2, height/2, (width * 0.75f)/2)

        noStroke()
    }

    override fun draw() {
        background(0xf5f2f0)
        boundary.drawBoundary()

        noStroke()

        val point = randomPoint()
        val cell = Cell(point.x, point.y)
        if(boundary contains cell) cell.colour = 0x33aa33
        cells.add(cell)

        cells.forEach(Cell::drawCell)

        if(cells.size > 250) cells.clear()
    }

    private fun randomPoint(): Point {
        val a = random(0f, 1f) * TWO_PI
        val r = (width * 0.85f)/2 * sqrt(random(0f, 1f))
        val x = r * cos(a)
        val y = r * sin(a)
        return Point(width/2 + x.toFloat(), height/2 + y.toFloat())
    }

    inner class Cell(x: Float, y: Float): Point(x, y) {
        var colour = 0xa9a9a9

        fun drawCell(){
            fill(colour, 0.2f)
            circle(x, y, 10)
        }
    }

    inner class Boundary(x: Int, y: Int, r: Float): Circle(x, y, r) {

        fun drawBoundary(){
            noFill()
            stroke(0x1d1d1d)
            circle(boundary.x, boundary.y, r)
        }
    }
}