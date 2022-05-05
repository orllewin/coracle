package coracle.shapes3d

import coracle.Matrix
import coracle.Vector3D
import coracle.shapes.Line
import coracle.times

class Line3D(var a: Vector3D, var b: Vector3D){
    companion object{
        fun random(): Line3D {
            return Line3D(Vector3D.random(),  Vector3D.random())
        }
    }

    fun applyZOffset(amount: Float){
        a.z += amount
        b.z += amount
    }

    fun to2D(projectionMatrix: Matrix): Line {
        val projA = (projectionMatrix * a).toPoint()
        val projB = (projectionMatrix * b).toPoint()
        return Line(projA, projB)
    }
}