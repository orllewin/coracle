package drawings.experiments.three_d

import coracle.*
import coracle.shapes3d.Mesh
import coracle.shapes.Point

class SphereMesh2: Drawing() {

    lateinit var cube: Mesh

    var projectionMatrix = Matrix.projectionMatrix(450f / 450f, 90.0f, 0.1f, 1000.0f)
    var matRotZ = Matrix()
    var matRotX = Matrix()

    val camera = Point()//Camera is at 0,0,0
    val light = Point(0.0f, 0.0f, -1.0f)

    override fun setup() {
        size(450, 450)

        cube = Mesh.sphere(30)
        light.normalise()

        noFill()
        stroke(0xffffff, 0.4f)
    }

    override fun draw() {
        background(0x1d1d1d)

        matRotZ.rotateZ(frame/240f)
        matRotX.rotateX(frame/180f)

        cube.triangles.forEach { triangle ->
            //Rotate
            val rotatedTriangle = triangle * matRotZ
            rotatedTriangle *= matRotX
            rotatedTriangle.applyZOffset(3f)

            val normal = rotatedTriangle.normal()

            if(normal.x * (rotatedTriangle.a.x - camera.x) + normal.y * (rotatedTriangle.a.y - camera.y) + normal.z * (rotatedTriangle.a.z - camera.z) < 0.0f) {
                // Illumination
                val dp = normal.x * light.x + normal.y * light.y + normal.z * light.z
                fill(Colour.grey((dp * 255f).toInt()))
                noStroke()

                val tri2D = rotatedTriangle.to2D(projectionMatrix)
                tri2D.scale(width/2f)
                tri2D.translate(width/2f, height/2f)
                tri2D.draw()
            }
        }
    }
}