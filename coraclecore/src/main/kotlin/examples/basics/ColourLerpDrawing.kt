package examples.basics

import coracle.Colour
import coracle.Drawing

class ColourLerpDrawing: Drawing() {

    override fun setup() {
        size(450, 450)

        noStroke()
    }

    override fun draw() {
        val colourA = Colour(0xf1d4dc)
        val colourB = Colour(0xc3bdd4)
        val colourC = Colour(0xebb9ce)
        val colourD = Colour(0x77bbd2)

        val columns = 10
        val columnWidth = width/columns

        val rows = 10
        val rowHeight = height/rows

        translate(columnWidth/2, rowHeight/2)

        repeat(rows){ r ->
            val colourR = Colour.lerp(colourA, colourB, (r)/rows.toFloat())
            repeat(columns){ c ->
                val colourC = Colour.lerp(colourC, colourD, (c)/columns.toFloat())
                fill(Colour.lerp(colourR, colourC, ((r*c))/(columns*rows).toFloat()))
                circle(c * columnWidth, r * rowHeight, (columnWidth * 0.75)/2)
            }
        }

        noLoop()
    }
}