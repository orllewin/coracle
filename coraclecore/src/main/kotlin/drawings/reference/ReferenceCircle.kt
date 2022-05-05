package drawings.reference

import coracle.Drawing
import coracle.random
import coracle.shapes.Circle
import coracle.shapes.Line

class ReferenceCircle: Drawing() {

    private val w = 450
    private val h = 450
    private val circleA = Circle(random(80, w-80), random(80, h - 80), random(30, 80))
    private val circleB = Circle(random(80, w-80), random(80, h - 80), random(30, 80))

    private val worldColour = 0xf5f2f0
    private val collideColour = 0x6d6d6d
    private val circleColour = 0xacacac
    private val lineColour = 0x000000

    private val aSpeed = random(1,4).toInt()
    private val bSpeed = random(1,4).toInt()

    private var line = Line()

    private var aXDirection = 1
    private var aYDirection = 1
    private var bXDirection = -1
    private var bYDirection = -1

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        background(worldColour)

        //Move A
        when {
            circleA.x > width - circleA.r || circleA.x < circleA.r -> aXDirection *= -1
        }
        when {
            circleA.y > height - circleA.r || circleA.y < circleA.r -> aYDirection *= -1
        }

        circleA.x = circleA.x + ( aSpeed * aXDirection )
        circleA.y = circleA.y + ( aSpeed * aYDirection )

        //Move B
        when {
            circleB.x > width - circleB.r || circleB.x < circleB.r -> bXDirection *= -1
        }
        when {
            circleB.y > height - circleB.r || circleB.y < circleB.r -> bYDirection *= -1
        }

        circleB.x = circleB.x + ( bSpeed * bXDirection )
        circleB.y = circleB.y + ( bSpeed * bYDirection )

        when {
            circleA.collides(circleB) -> fill(collideColour)
            else -> fill(circleColour)
        }

        noStroke()
        circleA.draw()
        circleB.draw()

        //Distances
        stroke(lineColour)
        line.update(circleA.origin(), circleB.origin())
        line.draw()

        noStroke()

        fill(lineColour)
        val edgePointA = circleA.edgePoint(circleB.origin())
        circle(edgePointA, 5)

        val edgePointB = circleB.edgePoint(circleA.origin())
        circle(edgePointB, 5)
    }
}