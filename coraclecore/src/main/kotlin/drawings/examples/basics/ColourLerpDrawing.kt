package drawings.examples.basics

import coracle.Colour
import coracle.Drawing

class ColourLerpDrawing: Drawing() {

    val columns = 10
    val columnWidth = 450/columns

    val rows = 10
    val rowHeight = 450/rows

    val colourA = Colour.random()
    val colourB = Colour.random()
    val colourC = Colour.random()
    val colourD = Colour.random()

    var elapsed = 0

    override fun setup() {
        size(450, 450)
        translate(columnWidth/2, rowHeight/2)
        noStroke()
    }

    override fun draw() {
        background(0xf5f2f0)
        elapsed++

        repeat(rows){ r ->
            val colourR = Colour.lerp(colourA, colourB, (r)/rows.toFloat())
            repeat(columns){ c ->
                val colourC = Colour.lerp(colourC, colourD, (c)/columns.toFloat())
                fill(Colour.lerp(colourR, colourC, ((r*c))/(columns*rows).toFloat()))
                circle(c * columnWidth, r * rowHeight, (columnWidth * 0.75)/2)
            }
        }

        if(elapsed == 100){
            elapsed = 0
            colourA.randomise()
            colourB.randomise()
            colourC.randomise()
            colourD.randomise()
        }
    }
}