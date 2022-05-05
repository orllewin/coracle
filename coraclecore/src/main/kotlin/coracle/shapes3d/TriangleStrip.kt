package coracle.shapes3d

import coracle.Math
import coracle.PI
import coracle.TWO_PI
import coracle.Vector3D
import kotlin.math.cos
import kotlin.math.sin

class TriangleStrip {

    var vertices = mutableListOf<Vector3D>()

    fun triangles(): Array<Triangle3D>{
        val triangles = mutableListOf<Triangle3D>()

        val verticesCount = vertices.size
        repeat(verticesCount - 2){ v ->
            if (v % 2 == 0){
                val v1 = vertices[v]
                val v2 = vertices[v + 1]
                val v3 = vertices[v + 2]

                triangles.add(Triangle3D(v1, v2, v3))
            }else{
                val v1 = vertices[v]
                val v2 = vertices[v + 2]
                val v3 = vertices[v + 1]
                triangles.add(Triangle3D(v1, v2, v3))
            }
        }

        return triangles.toTypedArray()
    }

    companion object{
        fun sphere(detail: Int): TriangleStrip {

            val vertices = Array(detail + 1) { Array(detail + 1){ Vector3D() } }

            val sphere = TriangleStrip()

            val r = 2f
            repeat(detail + 1){ i ->
                val lat: Float = Math.map(i.toFloat(), 0f, detail.toFloat(), 0f, PI.toFloat())
                repeat(detail + 1){ j ->
                    val lon: Float = Math.map(j.toFloat(), 0f, detail.toFloat(), 0f, TWO_PI.toFloat())
                    vertices[i][j].x = r * sin(lat) * cos(lon)
                    vertices[i][j].y = r * sin(lat) * sin(lon)
                    vertices[i][j].z = r * cos(lat)
                }
            }

            repeat(detail) { i ->
                repeat(detail) { j ->
                    sphere.vertices.add(vertices[i][j])
                    sphere.vertices.add(vertices[i + 1][j])
                }
            }

            return sphere
        }
    }
}