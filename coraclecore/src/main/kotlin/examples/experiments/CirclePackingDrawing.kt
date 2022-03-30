package examples.experiments

import coracle.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/*
    A basic brute-force approach to Circle Packing
 */
class CirclePackingDrawing: Drawing() {

    private var circles = mutableListOf<Circle>()

    override fun setup() {
        size(600, 600)
        noStroke()
    }

    override fun draw() {
        background(0xffffde)

        fill(0x88ddaa)
        noStroke()
        circle(width/2, height/2, 250)

        val rndCoord = coordWithinCircle()
        val rndCircle = Circle(rndCoord.x.toInt(), rndCoord.y.toInt(), 1)
        if(!collision(rndCircle)) circles.add(rndCircle)

        circles.forEach { circle -> circle.grow().draw() }
    }

    private fun coordWithinCircle(): Coord {
        val a = random(0f, 1f) * TWO_PI
        val r = 250f * sqrt(random(0f, 1f))
        val x = r * cos(a)
        val y = r * sin(a)
        return Coord(width/2 + x.toFloat(), height/2 + y.toFloat())
    }

    fun collision(circle: Circle): Boolean{
        circles.forEach { other ->
            if(other != circle && circle intersects other) return true
        }

        return false
    }



    inner class Circle(x: Int, y: Int, var r: Int){

        var growing = true
        val location = Vector(x, y)
        val color = color(random(150, 225), random(210, 255), random(200, 235))

        fun grow(): Circle{
            if(!growing) return this
            r++
            if(r > 15) growing = false
            if(collision(this)) growing = false
            return this
        }

        infix fun intersects(other: Circle): Boolean = location.distance(other.location) <= r + other.r

        fun draw(){
            fill(color, 0.5f)
            stroke(0x55aa88)
            circle(location.x, location.y, r)
        }
    }
}