package coracle.shapes

import coracle.Coord
import coracle.Easel
import coracle.svg
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

@Suppress("MemberVisibilityCanBePrivate")
open class Circle(var x: Float, var y: Float, var r: Float) {

    constructor(x: Number, y: Number, r: Number): this(x.toFloat(), y.toFloat(), r.toFloat())
    constructor(point: Point, r: Int): this(point.x, point.y, r)

    infix fun contains(point: Point): Boolean = ((x - point.x ) * (x - point.x )) + ((y - point.y ) * (y - point.y)) <= r * r

    fun originDistance(other: Circle): Float {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    fun edgeDistance(other: Circle): Float {
        val dx = x - other.x
        val dy = y - other.y
        return min(sqrt(dx * dx + dy * dy) - r - other.r, 0f)
    }

    fun edgePoint(other: Point): Point{
        val line = Line(x, y, other.x, other.y)
        val angle = line.angle()
        val xR = cos(angle) * r
        val yR = sin(angle) * r
        return Point(x + xR, y + yR)
    }

    fun collides(other: Circle): Boolean = originDistance(other) <= r + other.r

    fun toSVG(colour: String): String{
       return "<circle cx=\"${x.svg()}\" cy=\"${y.svg()}\" r=\"${r.svg()}\" style=\"fill:$colour\" />"
    }

    fun origin(): Point = Point(x, y)

    open fun draw(){
        Easel.drawing?.circle(this)
    }
}