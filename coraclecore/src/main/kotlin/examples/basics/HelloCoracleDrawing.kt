package examples.basics

import coracle.Drawing

class HelloCoracleDrawing: Drawing() {

    var x = 0

    override fun setup() {
        size(450, 200)
        if(isAndroid()) strokeWeight(5)
    }

    override fun draw() {
        background(0xf5f2f0)

        stroke(0xcbb0cc)
        line(x, 0, x, height/2)
        line(width/2 + x/2, height/2, width/2 + x/2, height)
        line( x/2, height/2 + height/4,  x/2, height)

        x += 2

        when {
            x > width -> x = 0
        }
    }
}