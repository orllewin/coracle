package coracle.shapes

import coracle.Coord

class Rect(var x: Float, var y: Float, var width: Float, var height: Float): Shape() {

    constructor(width: Int, height: Int) : this(0f, 0f, width.toFloat(), height.toFloat())
    constructor(point1: Point, point2: Point) : this(point1.x, point1.y, point2.x, point2.y)
    constructor(coord1: Coord, coord2: Coord) : this(coord1.x, coord1.y, coord2.x, coord2.y)
    constructor(x: Int, y: Int, width: Int, height: Int) : this(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())

    infix fun contains(point: Point): Boolean = point.x >= x && x <= x + width && point.y >= y && point.y <= y + height
    infix fun contains(circle: Circle): Boolean = circle.x >= x && x <= x + width && circle.y >= y && circle.y <= y + height

    fun isPortrait(): Boolean = height > width
    fun isLandscape(): Boolean = width > height
    fun isSquare(): Boolean = width == height
}