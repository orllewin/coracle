package examples.basics

import coracle.Colour
import coracle.Drawing

class ColourLerpDrawing: Drawing() {

    override fun setup() {
        size(450, 450)

        noStroke()
    }

    override fun draw() {
        val colourA = Colour(255, 0, 0)
        val colourB = Colour(0, 0, 255)
        val colourC = Colour(0, 255, 0)
        val colourD = Colour(255, 0, 255)

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