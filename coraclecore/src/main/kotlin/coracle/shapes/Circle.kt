package coracle.shapes

import coracle.Coord

open class Circle(var x: Float, var y: Float, var r: Float) {

    constructor(x: Number, y: Number, r: Number): this(x.toFloat(), y.toFloat(), r.toFloat())
    constructor(point: Point, r: Int): this(point.x, point.y, r)
    constructor(coord: Coord, r: Int): this(coord.x, coord.y, r)
}