package coracle.shapes3d

import coracle.*
import coracle.Math.map
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Mesh {

    companion object{

        private fun supershapeRadius(theta: Float, m: Float, n1: Float, n2: Float, n3: Float): Float {
            var t1: Float = abs(cos(m * theta / 4f))
            t1 = t1.pow(n2)
            var t2: Float = abs(sin(m * theta / 4f))
            t2 = t2.pow(n3)
            val t3 = t1 + t2
            return t3.pow((-1f / n1))
        }

        /*
            Adapted from: https://github.com/CodingTrain/website/blob/main/CodingChallenges/CC_026_SuperShape3D/Processing/CC_026_SuperShape3D/CC_026_SuperShape3D.pde
         */
        fun supershape(m: Int, detail: Int): Mesh {
            val supershape = Mesh()
            val vertices = Array(detail + 1) { Array(detail + 1){ Vector3D() } }
            val r = 1f
            repeat(detail + 1){ i ->
                val lat = map(i.toFloat(), 0f, detail.toFloat(), -TAU.toFloat(), TAU.toFloat())
                val r2: Float = supershapeRadius(lat, m.toFloat(), 0.2f, 1.7f, 1.7f)
                repeat(detail + 1){ j ->
                    val lon = map(j.toFloat(), 0f, detail.toFloat(), (-PI).toFloat(), PI.toFloat())
                    val r1: Float = supershapeRadius(lon, m.toFloat(), 0.2f, 1.7f, 1.7f)
                    vertices[i][j].x = r * r1 * cos(lon) * r2 * cos(lat)
                    vertices[i][j].y = r * r1 * sin(lon) * r2 * cos(lat)
                    vertices[i][j].z = r * r2 * sin(lat)
                }
            }

            repeat(detail) { i ->
                repeat(detail) { j ->
                    val v1 = vertices[i][j]
                    val v2 = vertices[i + 1][j]
                    val v3 = vertices[i][j + 1]
                    supershape.add(Triangle3D(v1, v2, v3))
                }
            }

            return supershape

        }

        fun sphere(detail: Int): Mesh {

            val vertices = Array(detail + 1) { Array(detail + 1){ Vector3D() } }

            val sphere = Mesh()
            val r = 2f
            repeat(detail + 1){ i ->
                val lat: Float = map(i.toFloat(), 0f, detail.toFloat(), 0f, PI.toFloat())
                repeat(detail + 1){ j ->
                    val lon: Float = map(j.toFloat(), 0f, detail.toFloat(), 0f, TWO_PI.toFloat())
                    vertices[i][j].x = r * sin(lat) * cos(lon)
                    vertices[i][j].y = r * sin(lat) * sin(lon)
                    vertices[i][j].z = r * cos(lat)
                }
            }

            repeat(detail) { i ->
                repeat(detail) { j ->
                    val v1 = vertices[i][j]
                    val v2 = vertices[i + 1][j]
                    val v3 = vertices[i][j + 1]
                    sphere.add(Triangle3D(v1, v2, v3))
                }
            }

            return sphere
        }

        fun cube(): Mesh {

            val cube = Mesh()

            //South
            cube.add(Triangle3D(0.0f,0.0f,0.0f,0.0f,1.0f,0.0f,1.0f,1.0f,0.0f))
            cube.add(Triangle3D(0.0f,0.0f,0.0f,1.0f,1.0f,0.0f,1.0f,0.0f,0.0f))

            //East
            cube.add(Triangle3D(1.0f,0.0f,0.0f,1.0f,1.0f,0.0f,1.0f,1.0f,1.0f))
            cube.add(Triangle3D(1.0f,0.0f,0.0f,1.0f,1.0f,1.0f,1.0f,0.0f,1.0f))

            //North
            cube.add(Triangle3D(1.0f,0.0f,1.0f,1.0f,1.0f,1.0f,0.0f,1.0f,1.0f))
            cube.add(Triangle3D(1.0f,0.0f,1.0f,0.0f,1.0f,1.0f,0.0f,0.0f,1.0f))

            //West
            cube.add(Triangle3D(0.0f,0.0f,1.0f,0.0f,1.0f,1.0f,0.0f,1.0f,0.0f))
            cube.add(Triangle3D(0.0f,0.0f,1.0f,0.0f,1.0f,0.0f,0.0f,0.0f,0.0f))

            //Top
            cube.add(Triangle3D(0.0f,1.0f,0.0f,0.0f,1.0f,1.0f,1.0f,1.0f,1.0f))
            cube.add(Triangle3D(0.0f,1.0f,0.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.0f))

            //Bottom
            cube.add(Triangle3D(1.0f,0.0f,1.0f,0.0f,0.0f,1.0f,0.0f,0.0f,0.0f))
            cube.add(Triangle3D(1.0f,0.0f,1.0f,0.0f,0.0f,0.0f,1.0f,0.0f,0.0f))

            //Move origin to center
            cube.triangles.forEach { tri ->
                tri.a.x *= 2f
                tri.a.y *= 2f
                tri.a.z *= 2f
                tri.a.x -= 1f
                tri.a.y -= 1f
                tri.a.z -= 1f

                tri.b.x *= 2f
                tri.b.y *= 2f
                tri.b.z *= 2f
                tri.b.x -= 1f
                tri.b.y -= 1f
                tri.b.z -= 1f

                tri.c.x *= 2f
                tri.c.y *= 2f
                tri.c.z *= 2f
                tri.c.x -= 1f
                tri.c.y -= 1f
                tri.c.z -= 1f
            }
            return cube
        }
    }
    var triangles =  mutableListOf<Triangle3D>()

    fun add(triangle: Triangle3D){
        triangles.add(triangle)
    }
}