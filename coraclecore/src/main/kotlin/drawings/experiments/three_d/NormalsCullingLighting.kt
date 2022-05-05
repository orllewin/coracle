package drawings.experiments.three_d

import coracle.*
import coracle.shapes3d.Mesh
import coracle.shapes.Point

/*
    Code-It-Yourself! 3D Graphics Engine Part #2 - Normals, Culling, Lighting
    Adapted from: https://github.com/OneLoneCoder/videos/blob/master/OneLoneCoder_olcEngine3D_Part2.cpp
    See: https://youtu.be/XgMWc6LumG4
    */
class NormalsCullingLighting: Drawing() {

    lateinit var cube: Mesh

    var projectionMatrix = Matrix.projectionMatrix(450f / 450f, 90.0f, 0.1f, 1000.0f)
    var matRotZ = Matrix()
    var matRotX = Matrix()

    val camera = Point()//Camera is at 0,0,0
    val light = Point(0.0f, 0.0f, -1.0f)

    override fun setup() {
        size(450, 450)

        cube = Mesh.cube()

        light.normalise()
    }

    override fun draw() {
        background(0x1d1d1d)

        val f = frame / 20.0f
        matRotZ.rotateZ(f)
        matRotX.rotateX(f * 0.5f)

        cube.triangles.forEach { triangle ->
            val rotatedTriangle = triangle * matRotZ
            rotatedTriangle *= matRotX
            rotatedTriangle.applyZOffset(3f)

            val normal = rotatedTriangle.normal()

            if(normal.x * (rotatedTriangle.a.x - camera.x) + normal.y * (rotatedTriangle.a.y - camera.y) + normal.z * (rotatedTriangle.a.z - camera.z) < 0.0f) {
                // Illumination
                val dp = normal.x * light.x + normal.y * light.y + normal.z * light.z
                val colourVal = dp * 255f
                fill(Colour.grey(colourVal.toInt()))
                noStroke()

                val tri2D = rotatedTriangle.to2D(projectionMatrix)
                tri2D.scale(width/2f)
                tri2D.translate(width/2f, height/2f)
                tri2D.draw()
            }
        }
    }
}