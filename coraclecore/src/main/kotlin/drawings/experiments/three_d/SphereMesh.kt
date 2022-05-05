package drawings.experiments.three_d

import coracle.*
import coracle.shapes3d.Mesh

class SphereMesh: Drawing() {

    lateinit var cube: Mesh

    var projectionMatrix = Matrix.projectionMatrix(450f / 450f, 90.0f, 0.1f, 1000.0f)
    var matRotZ = Matrix()
    var matRotX = Matrix()

    override fun setup() {
        size(450, 450)

        cube = Mesh.sphere(40)

        noFill()
        stroke(0xffffff, 0.4f)
    }

    override fun draw() {
        background(0x1d1d1d)

        val f = frame/40.0f

        matRotZ.rotateZ(f * 0.2f)
        matRotX.rotateX(f * 0.2f)

        cube.triangles.forEach { triangle ->
            //Rotate
            val rotatedTriangle = triangle * matRotZ
            rotatedTriangle *= matRotX
            rotatedTriangle.applyZOffset(3f)

            //Project to 2D
            val tri2D = rotatedTriangle.to2D(projectionMatrix)
            tri2D.scale(width / 2f)
            tri2D.translate(width / 2f, height / 2f)
            tri2D.draw()
        }
    }
}