package coracle.shapes

import coracle.Degree
import coracle.Math.rad
import kotlin.math.cos
import kotlin.math.sin

open class Point(var x: Float, var y: Float): Shape() {

    constructor(x: Int, y: Int): this(x.toFloat(), y.toFloat())
    constructor(x: Double, y: Double): this(x.toFloat(), y.toFloat())

    fun plot(angle: Degree, distance: Float): Point {
        return Point(this.x + (distance * cos(angle.rad())), this.y + (distance * sin(angle.rad())))
    }
}