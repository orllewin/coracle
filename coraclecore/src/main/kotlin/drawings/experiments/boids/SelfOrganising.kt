package drawings.experiments.boids

import coracle.Drawing
import coracle.Vector
import coracle.random
import coracle.randomInt

class SelfOrganising: Drawing() {

    val worldColour = 0xf5f2f0
    val boidColour = 0x000000
    var boids = mutableListOf<Boid>()
    val cellWidth = 30

    var inited = false

    val birthWaitMax = 50
    var birthWait = randomInt(birthWaitMax)

    private lateinit var blackHole: Vector


    override fun setup() {
        size(450, 450)
        blackHole = Vector(width/2, height/2)
        boids.add(Boid())
    }

    override fun draw() {
        background(worldColour)

        if(frame >= birthWait && boids.size < 150){
            boids.add(Boid())
            frame = 0
            birthWait = randomInt(birthWaitMax)
        }

        boids.forEach { boid ->
            boid.update().draw()
        }
    }

    inner class Boid{

        private val maxSpeed = 0.3f
        private var location = Vector(width/2 + random(-5, 5), height/2 + random(-5, 5))
        private var velocity = Vector(0, 0)

        fun update(): Boid {

            var blackholeDirection = blackHole - location
            blackholeDirection.normalize()
            blackholeDirection *= 0.05f

            velocity += blackholeDirection
            location += velocity

            var closestDistance = Float.MAX_VALUE
            var closestIndex = -1

            if(boids.size == 1) return this

            boids.forEachIndexed { index, other ->
                if(other != this){
                    val distance = location.distance(other.location)
                    if(distance < closestDistance){
                        closestIndex = index
                        closestDistance = distance
                    }
                }

                var direction = location.direction(other.location)
                direction.normalize()
                direction *= (0.002f)/boids.size.toFloat()

                velocity += (direction)
                velocity.limit(maxSpeed)

            }

            val closest = boids[closestIndex]

            if(closestDistance < cellWidth) {
                var direction = location.direction(closest.location)
                direction.normalize()
                direction *= -0.4f

                velocity += (direction)
                velocity.limit(maxSpeed)

            }

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

            fill(boidColour, 0.7f)
            circle(location.x, location.y, 1)
        }
    }
}
