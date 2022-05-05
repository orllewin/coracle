package coracle

import coracle.shapes3d.Line3D
import coracle.shapes3d.Triangle3D
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Matrix {

    val arr = Array(4) { FloatArray(4) }

    init {

    }

    companion object{
        fun projectionMatrix(aspectRatio: Float, fieldOfView: Float, near: Float, far: Float): Matrix{
            val fovRad: Float = 1.0f / tan(fieldOfView * 0.5f / 180.0f * PI).toFloat()
            val matProj = Matrix()
            matProj[0][0] = aspectRatio * fovRad
            matProj[1][1] = fovRad
            matProj[2][2] = far / (far - near)
            matProj[3][2] = (-far * near) / (far - near)
            matProj[2][3] = 1f
            matProj[3][3] = 0f

            return matProj
        }
    }

    operator fun get(i: Int): FloatArray {
        return arr[i]
    }

    fun rotateX(angle: Float){
        arr[0][0] = 1f
        arr[1][1] = cos(angle)
        arr[1][2] = sin(angle)
        arr[2][1] = -sin(angle)
        arr[2][2] = cos(angle)
        arr[3][3] = 1f
    }

    fun rotateY(angle: Float){
        arr[0][0] = cos(angle)
        arr[0][2] = -sin(angle)
        arr[1][1] = 1f
        arr[2][0] = sin(angle)
        arr[2][2] = cos(angle)
        arr[3][3] = 1f
    }

    fun rotateZ(angle: Float){
        arr[0][0] = cos(angle)
        arr[0][1] = sin(angle)
        arr[1][0] = -sin(angle)
        arr[1][1] = cos(angle)
        arr[2][2] = 1f
        arr[3][3] = 1f
    }


}

operator fun Triangle3D.times(m: Matrix): Triangle3D = Triangle3D(m * a, m * b, m * c)
operator fun Line3D.times(m: Matrix): Line3D = Line3D(m * a, m * b)

operator fun Matrix.times(point: Vector3D): Vector3D = this.multiply(point)
operator fun Vector3D.times(matrix: Matrix): Vector3D = matrix.multiply(this)

operator fun Triangle3D.timesAssign(m: Matrix){
    this.a = this.a * m
    this.b = this.b * m
    this.c = this.c * m
}
operator fun Line3D.timesAssign(m: Matrix){
    this.a = this.a * m
    this.b = this.b * m
}

fun Matrix.multiply(point: Vector3D): Vector3D{
    val transformed = Vector3D()
    transformed.x = point.x * this[0][0] + point.y * this[1][0] + point.z * this[2][0] + this[3][0]
    transformed.y = point.x * this[0][1] + point.y * this[1][1] + point.z * this[2][1] + this[3][1]
    transformed.z = point.x * this[0][2] + point.y * this[1][2] + point.z * this[2][2] + this[3][2]
    val w = point.x * this[0][3] + point.y * this[1][3] + point.z * this[2][3] + this[3][3]

    if (w != 0.0f) {
        transformed.x /= w
        transformed.y /= w
        transformed.z /= w
    }

    return transformed
}