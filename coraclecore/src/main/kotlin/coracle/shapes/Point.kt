package coracle.shapes

import coracle.Degree
import coracle.Easel
import coracle.Math.rad
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

open class Point(var x: Float, var y: Float, var z: Float = 0f): Shape() {

    constructor(x: Int, y: Int): this(x.toFloat(), y.toFloat())
    constructor(x: Double, y: Double): this(x.toFloat(), y.toFloat())
    constructor(): this(0f, 0f)

    fun plot(angle: Degree, distance: Float): Point {
        return Point(this.x + (distance * cos(angle.rad())), this.y + (distance * sin(angle.rad())))
    }

    fun normalise(){
        val l = sqrt(x * x + y * y + z * z)
        x /= l
        y /= l
        z /= l
    }

    fun scale(amount: Float){
        x *= amount
        y *= amount
    }

    fun translate(x: Float, y: Float){
        this.x += x
        this.y += y
    }

    fun copy(): Point = Point(x, y)

    fun draw(){
        Easel.drawing?.point(this)
    }
}