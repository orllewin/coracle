
import coracle.*
import coracle.shapes3d.Line3D


class Lines3D: Drawing() {

    var projectionMatrix = Matrix.projectionMatrix(450f / 450f, 90.0f, 0.1f, 1000.0f)
    var matRotX = Matrix()
    var matRotY = Matrix()
    var matRotZ = Matrix()

    var lines = Array(120){ Line3D.random()}

    override fun setup() {
        size(450, 450)
        stroke(0xffffff)
    }

    override fun draw() {
        background(0x1d1d1d)

        matRotX.rotateX(frame/240f)
        matRotY.rotateY(frame/120f)
        matRotZ.rotateZ(frame/240f)

        lines.forEach{ line ->
            val rotatedLine = line * matRotX
            rotatedLine *= matRotY
            rotatedLine *= matRotZ

            val z = (rotatedLine.a.z + rotatedLine.b.z)/2
            val shade = Math.map(z, -1.5f, 1.1f, 255f, 29f)
            stroke(Color.grey(shade.toInt()), 0.6f)

            rotatedLine.applyZOffset(2f)

            val line2D = rotatedLine.to2D(projectionMatrix)
            line2D.scale(width/2f)
            line2D.translate(width/2f, height/2f)
            line2D.draw()
        }
    }
}
    