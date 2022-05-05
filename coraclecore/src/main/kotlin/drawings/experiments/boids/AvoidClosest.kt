package drawings.experiments.boids

import coracle.Drawing
import coracle.Vector
import coracle.shapes.Line

class AvoidClosest: Drawing() {

    val worldColour = 0xf5f2f0
    val boidColour = 0x000000
    lateinit var boids: Array<Boid>
    val cellWidth = 45

    var inited = false

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        matchWidth()

        if(!inited){
            boids = Array(120){ Boid() }
            inited = true
        }

        background(worldColour)

        boids.forEach { boid ->
            boid.update().draw()
        }
    }

    inner class Boid{

        private val maxSpeed = 1.5f
        private var location = Vector.randomPosition(width, height)
        private var velocity = Vector(0, 0)

        fun update(): Boid {

            var closestDistance = Float.MAX_VALUE
            var closestIndex = -1

            boids.forEachIndexed { index, other ->
                if(other != this){
                    val distance = location.distance(other.location)
                    if(distance < closestDistance){
                        closestIndex = index
                        closestDistance = distance
                    }
                }
            }

            val closest = boids[closestIndex]

            //While we've got the reference to cloests boid - draw the relationship:
            val line = Line(location.x, location.y, closest.location.x, closest.location.y)
            val midpoint = line.midpoint()
            stroke(boidColour, 0.1f)
            line(line)
            fill(boidColour, 0.1f)
            noStroke()
            circle(midpoint, 4)

            var direction = location.direction(closest.location)
            direction.normalize()
            direction *= -0.2f

            velocity += (direction)
            velocity.limit(maxSpeed)
            location += (velocity)

            if (this.location.x > width + cellWidth) this.location.x = -cellWidth.toFloat()
            if (this.location.x < -cellWidth) this.location.x = width.toFloat() + cellWidth
            if (this.location.y > height + cellWidth) this.location.y = -cellWidth.toFloat()
            if (this.location.y < -cellWidth) this.location.y = height.toFloat() + cellWidth

            return this
        }

        fun draw(){
            noStroke()
            fill(boidColour, 0.15f)
            circle(location.x, location.y, (cellWidth/2))

            fill(boidColour)
            circle(location.x, location.y, 4)
        }
    }
}