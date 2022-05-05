package drawings.experiments.circle_packing

import coracle.Drawing
import coracle.TWO_PI
import coracle.Vector
import coracle.random
import coracle.shapes.Point
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class StandardCirclePacking: Drawing() {

    val worldColour = 0xf5f2f0
    val boundsColour = 0xE5E2E0
    val black = 0x000000

    val circles = mutableListOf<Circle>()
    var currentRadius = 15
    var attemptCycles = 300
    var emptyCycles = 0

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        background(worldColour)

        fill(boundsColour)
        noStroke()
        circle(width / 2, height / 2, 200)

        repeat(50) {
            val point = coordWithinCircle()
            val rndCircle = Circle(point.x.toInt(), point.y.toInt(), currentRadius)
            when {
                collision(rndCircle) -> emptyCycles++
                else -> {
                    emptyCycles = 0
                    circles.add(rndCircle)
                }
            }
        }

        if(emptyCycles >= attemptCycles){
            currentRadius--
            emptyCycles = 0
            attemptCycles += 100
        }

        noFill()
        stroke(black, 0.5f)
        circles.forEach { circle -> circle.draw() }

        circles.sortByDescending { c ->
            c.r
        }

        if(currentRadius == 0) noLoop()
    }

    private fun coordWithinCircle(): Point {
        val a = random(0f, 1f) * TWO_PI
        val r = 200f * sqrt(random(0f, 1f))
        val x = r * cos(a)
        val y = r * sin(a)
        return Point(width/2 + x.toFloat(), height/2 + y.toFloat())
    }

    fun collision(circle: Circle): Boolean{
        circles.forEach { other ->
            if(other != circle && circle intersects other) return true
        }

        return false
    }

    inner class Circle(x: Int, y: Int, var r: Int){
        val location = Vector(x, y)

        infix fun intersects(other: Circle): Boolean = location.distance(other.location) <= r + other.r

        fun draw() = circle(location.x, location.y, r)
    }
}
