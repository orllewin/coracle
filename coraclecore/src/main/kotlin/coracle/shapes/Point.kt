package coracle.shapes

open class Point(var x: Float, var y: Float): Shape() {

    constructor(x: Int, y: Int): this(x.toFloat(), y.toFloat())
    constructor(x: Double, y: Double): this(x.toFloat(), y.toFloat())
}