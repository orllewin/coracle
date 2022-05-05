package drawings.examples.basics

import coracle.Color
import coracle.Colour
import coracle.Drawing

/*
    You can create colours in different ways
    the quickest way for the computer is to use hexadecimal notation, eg. magenta: 0xff00cc
    If you find that confusing you can instead use red green and blue values from 0 to 255:
    val red = color(255, 0, 0)
    val green = color(0, 255, 0)
    val blue = color(0, 0, 255)

    For interpolating between colours there's the Colour object: val grey = Colour(100, 100, 100)
    Using Colour lets you use Colour.lerp(a, b, t) which calculates a colour between two others.
 */
class UsingColoursDrawing: Drawing() {

    override fun setup() {
        size(400, 125)
        noStroke()
    }

    override fun draw() {

        background(0xf5f2f0)

        var y = 40
        val r = 15

        fill(Colour(255, 0, 200))//Colour object
        circle(30, y, r)

        fill(Colour(200, 0, 255))
        circle(70, y, r)

        fill(Color(0, 200, 255))//Color alias, same as Colour
        circle(110, y, r)

        fill(0x00ccff)//Hexadecimal notation Int
        circle(150, y, r)

        //Line only, no fill
        stroke(0x111fff)
        noFill()
        circle(190, y, r)

        //Outline, and fill
        stroke(0x000000)
        fill(0xffffff)
        circle(230, y, r)

        //Colour Lerp
        val a = Color(50, 60, 200)
        val b = Color(200, 50, 255)
        fill(Colour.lerp(a, b, 0.1f))
        noStroke()
        circle(230, y, r)

        fill(Colour.lerp(a, b, 0.2f))
        circle(245, y, r)

        fill(Colour.lerp(a, b, 0.3f))
        circle(260, y, r)

        fill(Colour.lerp(a, b, 0.4f))
        circle(275, y, r)

        fill(Colour.lerp(a, b, 0.5f))
        circle(290, y, r)

        fill(Colour.lerp(a, b, 0.6f))
        circle(305, y, r)

        fill(Colour.lerp(a, b, 0.7f))
        circle(320, y, r)

        fill(Colour.lerp(a, b, 0.8f))
        circle(335, y, r)

        fill(Colour.lerp(a, b, 0.9f))
        circle(350, y, r)

        fill(Colour.lerp(a, b, 1f))
        circle(365, y, r)

        //Using brighter
        var c = Colour(80, 50, 120)
        y = 90
        fill(c)
        circle(30, y, r)

        c.brighten()
        fill(c)
        circle(60, y, r)

        c.brighten()
        fill(c)
        circle(90, y, r)

        c.brighten()
        fill(c)
        circle(120, y, r)

        c.brighten()
        fill(c)
        circle(150, y, r)

        c = Colour(120, 80, 50)
        fill(c)
        circle(180, y, r)

        c.darken()
        fill(c)
        circle(210, y, r)

        c.darken()
        fill(c)
        circle(240, y, r)

        c.darken()
        fill(c)
        circle(270, y, r)

        c.darken()
        fill(c)
        circle(300, y, r)
    }
}