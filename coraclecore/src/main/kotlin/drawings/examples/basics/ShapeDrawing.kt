package drawings.examples.basics

import coracle.Drawing

class ShapeDrawing: Drawing() {
    override fun setup() {
        size(740, 740)
        noStroke()
    }

    override fun draw() {
        background(0xf5f2f0)

        val cR = 90
        val sD = 180

        fill(0xea4941)
        square(100, 100, sD)
        circle(100, 280, cR)
        square(100, 460, sD)
        circle(100, 640, cR)

        fill(0x35363a)
        circle(280, 100, cR)
        square(280, 280, sD)
        circle(280, 460, cR)
        square(280, 640, sD)

        fill(0x3F5BED)
        square(460, 100, sD)
        circle(460, 280, cR)
        square(460, 460, sD)
        circle(460, 640, cR)

        fill(0xF3D327)
        circle(640, 100, cR)
        square(640, 280, sD)
        circle(640, 460, cR)
        square(640, 640, sD)

        noLoop()
    }
}