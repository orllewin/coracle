package examples.basics

import coracle.Drawing
import coracle.color

/*
    You can create colours in different ways
    the quickest way for the computer is to use hexadecimal notation, eg. magenta: 0xff00cc
    If you find that confusing you can instead use red green and blue values from 0 to 255:
    val red = color(255, 0, 0)
    val green = color(0, 255, 0)
    val blue = color(0, 0, 255)
 */
class UsingColoursDrawing: Drawing() {

    private val background = color(255, 225, 225)

    var x = 100

    override fun setup() {
        size(300, 300)
        noStroke()
    }

    override fun draw() {
        background(background)
        noStroke()

        fill(color(100, 200, 100))
        circle(110, 110, 80)

        fill(color(100, 200, 200))
        square(220, 220, 200)

        x += 1

        if(x >= width) x = 0

        stroke(0xff6666)
        line(x, 230, x, 270)
    }
}