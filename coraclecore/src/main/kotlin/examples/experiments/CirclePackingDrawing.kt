package examples.experiments

import coracle.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

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

    private fun inBoundary(circle: Circle): Boolean{
        val squareDistance = ((width/2 - circle.location.x) * (width/2 - circle.location.x)) + ((height/2 - circle.location.y) * (height/2 - circle.location.y))
        return squareDistance <= 250 * 250
    }

    fun collision(circle: Circle): Boolean{
        circles.forEach { other ->
            if(other != circle){
                if(circle.location.distance(other.location) <= circle.r + other.r){
                    return true
                }
            }
        }

        return false
    }

    inner class Circle(x: Int, y: Int, var r: Int){

        var growing = true
        val location = Vector(x, y)
        val color = color(random(150, 235), random(210, 255), random(150, 235))

        fun grow(): Circle{
            if(!growing) return this

            r++

            //if(r > 20) growing = false

            if(collision(this)) growing = false

            return this
        }

        fun draw(){
            fill(color, 0.5f)
            stroke(0x55aa88)
            circle(location.x, location.y, r)
        }
    }
}