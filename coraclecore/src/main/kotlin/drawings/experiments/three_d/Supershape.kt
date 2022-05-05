package drawings.experiments.three_d

import coracle.*
import coracle.Math.map
import coracle.shapes3d.Mesh

class Supershape: Drawing() {

    lateinit var supershape: Mesh
    var projectionMatrix = Matrix.projectionMatrix(450f / 450f, 90.0f, 0.1f, 1000.0f)
    var matRotX = Matrix()
    var matRotY = Matrix()
    var matRotZ = Matrix()

    override fun setup() {
        size(450, 450)
        supershape = Mesh.supershape(4, 50)
    }

    override fun draw() {
        background(0x1d1d1d)

        matRotX.rotateX(frame/240f)
        matRotY.rotateY(frame/120f)
        matRotZ.rotateZ(frame/240f)

        supershape.triangles.forEachIndexed { index, triangle ->
            val rotatedTriangle = triangle * matRotX
            rotatedTriangle *= matRotY
            rotatedTriangle *= matRotZ
            rotatedTriangle.applyZOffset(1.5f)

            val z = (rotatedTriangle.a.z + rotatedTriangle.b.z + rotatedTriangle.c.z)/3
            val shade = map(z, 0.5f, 2.5f, 255f, 29f)
            stroke(Color.grey(shade.toInt()))

            val tri2D = rotatedTriangle.to2D(projectionMatrix)
            tri2D.scale(width/2f)
            tri2D.translate(width/2f, height/2f)

            point(tri2D.a)
            point(tri2D.b)
            point(tri2D.c)
        }
    }
}