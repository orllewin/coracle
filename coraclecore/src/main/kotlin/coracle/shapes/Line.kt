package coracle.shapes

class Line(var x1: Float, var y1: Float, var x2: Float, var y2: Float) {
    constructor(x1: Int, y1: Int, x2: Int, y2: Int): this(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
    constructor(pA: Point, pB: Point): this(pA.x, pA.y, pB.x, pB.y)
}