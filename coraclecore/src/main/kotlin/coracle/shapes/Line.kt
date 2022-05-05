package coracle.shapes

import coracle.Easel
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min

class Line(var x1: Float, var y1: Float, var x2: Float, var y2: Float) {
    constructor(x1: Int, y1: Int, x2: Int, y2: Int): this(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat())
    constructor(pA: Point, pB: Point): this(pA.x, pA.y, pB.x, pB.y)
    constructor(): this(0f, 0f, 0f, 0f)

    fun midpoint(): Point = Point((x1 + x2)/2, (y1 + y2)/2)
    fun angle(): Float = atan2(y2 - y1, x2 - x1)
    fun intersects(other: Line): Point?{
        //todo - check if parallel
        val a1 = y2 - y1
        val b1 = x1 - x2
        val c1 = a1 * x1 + b1 * y1

        val a2 = other.y2 - other.y1
        val b2 = other.x1 - other.x2
        val c2 = a2 * other.x1 + b2 * other.y1

        val delta = a1 * b2 - a2 * b1

        val iX = (b2 * c1 - b1 * c2) / delta
        val iY = (a1 * c2 - a2 * c1) / delta

        return when {
            min(x1, x2) < iX && max(x1, x2) > iX && min(other.x1, other.x2) < iX && max(other.x1, other.x2) > iX -> Point(iX, iY)
            else -> null
        }
    }

    fun update(pA: Point, pB: Point){
        x1 = pA.x
        y1 = pA.y
        x2 = pB.x
        y2 = pB.y
    }

    fun scale(amount: Float){
        x1 *= amount
        y1 *= amount
        x2 *= amount
        y2 *= amount
    }

    fun translate(x: Float, y: Float){
        x1 += x
        y1 += y
        x2 += x
        y2 += y
    }

    fun draw(){
        Easel.drawing?.line(this)
    }
}