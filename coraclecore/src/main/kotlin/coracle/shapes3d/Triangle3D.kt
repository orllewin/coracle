package coracle.shapes3d

import coracle.Matrix
import coracle.Vector3D
import coracle.shapes.Triangle
import coracle.times

class Triangle3D(var a: Vector3D, var b: Vector3D, var c: Vector3D) {
    constructor(a1: Float, b1: Float, c1: Float, a2: Float, b2: Float, c2: Float, a3: Float, b3: Float, c3: Float): this(Vector3D(a1, b1, c1), Vector3D(a2, b2, c2), Vector3D(a3, b3, c3))

    fun applyZOffset(amount: Float){
        a.z += amount
        b.z += amount
        c.z += amount
    }

    fun normal(): Vector3D{
        val cp1 = Vector3D()
        cp1.x = b.x - a.x
        cp1.y = b.y - a.y
        cp1.z = b.z - a.z

        val cp2 = Vector3D()
        cp2.x = c.x - a.x
        cp2.y = c.y - a.y
        cp2.z = c.z - a.z

        val normal = Vector3D()
        normal.x = cp1.y * cp2.z - cp1.z * cp2.y
        normal.y = cp1.z * cp2.x - cp1.x * cp2.z
        normal.z = cp1.x * cp2.y - cp1.y * cp2.x
        normal.normalise()

        return normal
    }

    fun to2D(projectionMatrix: Matrix): Triangle {
        val projA = (projectionMatrix * a).toPoint()
        val projB = (projectionMatrix * b).toPoint()
        val projC = (projectionMatrix * c).toPoint()
        return Triangle(projA, projB, projC)
    }
}