package examples.basics

import coracle.Drawing

class HelloCoracleDrawing: Drawing() {

    var y = 100

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        background(DEFAULT_BACKGROUND)

        y -= 1

        when {
            y < 0 -> y = height
        }

        stroke(DEFAULT_FOREGROUND)
        line(0, y, width/2, y)
        line(width/2, y/2, width, y/2)
    }
}