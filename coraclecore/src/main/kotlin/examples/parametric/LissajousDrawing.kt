package examples.parametric

import coracle.Coord
import coracle.Drawing
import coracle.TWO_PI
import coracle.random
import kotlin.math.cos
import kotlin.math.sin

class LissajousDrawing: Drawing() {

    private var waveLengthA = 0.0f
    private var waveLengthB = 0.0f
    private var frame = 0
    private var angle = 0f
    private val coords = arrayListOf<Coord>()
    private var coordA: Coord? = null
    private var coordB: Coord? = null

    private val scale = 180

    override fun setup() {
        size(450, 450)
        translate(width/2, height/2)
        initialise()
    }

    private fun initialise(){
        coords.clear()
        frame = 0
        angle = 0f
        waveLengthA = random(25.0, 100.0)
        waveLengthB = random(25.0, 100.0)
    }

    override fun draw() {
        background(DEFAULT_BACKGROUND)

        if(frame > 3000) initialise()

        angle = (frame/ waveLengthA * TWO_PI).toFloat()
        val y = sin(angle) * scale
        angle = (frame/ waveLengthB * TWO_PI).toFloat()
        val x = cos(angle) * scale
        coords.add(Coord(x.toInt(), y.toInt()))

        stroke(DEFAULT_FOREGROUND)

        for(index in 1 until coords.size){
            coordA = coords[index -1]
            coordB = coords[index]
            line(coordA!!.x, coordA!!.y, coordB!!.x, coordB!!.y)
        }

        frame++
        angle += 0.1f
    }
}