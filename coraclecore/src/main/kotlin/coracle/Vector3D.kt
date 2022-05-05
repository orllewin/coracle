package coracle

import coracle.shapes.Line
import coracle.shapes.Point
import kotlin.math.sqrt

class Vector3D(var x: Float, var y: Float, var z: Float) {

    constructor(): this(0f, 0f, 0f)

    fun normalise(){
        val l = sqrt(x * x + y * y + z * z)
        x /= l
        y /= l
        z /= l
    }

    fun applyZOffset(amount: Float){
        z += amount
    }

    fun toPoint(): Point = Point(x, y, z)

    fun to2D(projectionMatrix: Matrix): Point = (projectionMatrix * this).toPoint()

    companion object{
        fun random(): Vector3D{
            return Vector3D(random(-1f, 1f), random(-1f, 1f), random(-1f, 1f))
        }
    }
}