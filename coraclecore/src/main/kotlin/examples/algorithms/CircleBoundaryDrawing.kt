package examples.algorithms

import coracle.Drawing
import coracle.Math
import coracle.random
import coracle.shapes.Circle
import coracle.shapes.Point

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
        background(DEFAULT_BACKGROUND)
    }

    override fun draw() {
        background(DEFAULT_BACKGROUND)
        boundary.drawBoundary()

        noStroke()

        val cell = Cell(random(cellRadius, width - cellRadius), random(cellRadius, height - cellRadius))
        if(boundary contains cell) cell.colour = 0xff00cc
        cells.add(cell)

        cells.forEach(Cell::drawCell)

        if(cells.size > 250) cells.clear()
    }

    inner class Cell(x: Float, y: Float): Point(x, y) {
        var colour = DEFAULT_FOREGROUND

        fun drawCell(){
            fill(colour, 0.1f)
            circle(x, y, cellRadius)

            fill(0x1d1d1d, 0.5f)
            circle(x, y, 2)
        }
    }

    inner class Boundary(x: Int, y: Int, r: Float): Circle(x, y, r) {

        infix fun contains(point: Point): Boolean =
            ((x - point.x ) * (x - point.x )) + ((y - point.y ) * (y - point.y)) <= r * r

        fun drawBoundary(){
            noFill()
            stroke(DEFAULT_FOREGROUND, 0.15f)
            circle(boundary.x, boundary.y, r)
        }
    }
}