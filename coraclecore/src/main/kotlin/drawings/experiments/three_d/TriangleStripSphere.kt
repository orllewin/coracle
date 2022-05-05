package drawings.experiments.three_d

import coracle.*
import coracle.shapes.Point
import coracle.shapes3d.Triangle3D
import coracle.shapes3d.TriangleStrip

class TriangleStripSphere: Drawing() {

    lateinit var sphere: TriangleStrip
    lateinit var triangles: Array<Triangle3D>
    var projectionMatrix = Matrix.projectionMatrix(450f / 450f, 90.0f, 0.1f, 1000.0f)
    var matRotX = Matrix()
    var matRotY = Matrix()
    var matRotZ = Matrix()

    val camera = Point()//Camera is at 0,0,0
    val light = Point(0.0f, 0.0f, -1.0f)

    override fun setup() {
        size(450, 450)
        sphere = TriangleStrip.sphere(42)
        triangles = sphere.triangles()
        noStroke()
    }

    override fun draw() {
        background(0x1d1d1d)

        matRotX.rotateX(frame/240f)
        matRotY.rotateY(frame/120f)
        matRotZ.rotateZ(frame/240f)

        triangles.forEach { triangle ->
            val rotatedTriangle = triangle * matRotX
            rotatedTriangle *= matRotY
            rotatedTriangle *= matRotZ
            rotatedTriangle.applyZOffset(3f)

            val normal = rotatedTriangle.normal()

            if(normal.x * (rotatedTriangle.a.x - camera.x) + normal.y * (rotatedTriangle.a.y - camera.y) + normal.z * (rotatedTriangle.a.z - camera.z) < 0.0f) {
                val dp = normal.x * light.x + normal.y * light.y + normal.z * light.z
                val colourVal = dp * 255f
                fill(Colour.grey(colourVal.toInt()))

                val tri2D = rotatedTriangle.to2D(projectionMatrix)
                tri2D.scale(width/2f)
                tri2D.translate(width/2f, height/2f)
                tri2D.draw()
            }
        }
    }
}