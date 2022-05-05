package drawings.experiments.boids

import coracle.Drawing
import coracle.Vector
import coracle.random
import coracle.randomInt
import coracle.shapes.Circle

class SelfOrganising2: Drawing() {

    val worldColour = 0x1d1d1d
    val boidColour = 0xffffff
    val nucleusColour = 0xffffff
    var boids = mutableListOf<Boid>()

    var inited = false

    val birthWaitMax = 30
    var birthWait = randomInt(birthWaitMax)

    private lateinit var blackHole: Vector

    var maxRadius = 15
    var nucleusRadius = 1

    override fun setup() {
        size(450, 450)
        blackHole = Vector(width/2, height/2)
        boids.add(Boid(width/2f, height/2f, 1f))

        if(isAndroid()){
            maxRadius = 45
            nucleusRadius = 4
        }
    }

    override fun draw() {
        background(worldColour)

        if(frame >= birthWait && boids.size < 150){
            boids.add(Boid(width/2 + random(-5, 5), height/2 + random(-5, 5), 1f))
            frame = 0
            birthWait = randomInt(birthWaitMax)
        }

        boids.forEach { boid ->
            boid.update().draw()
        }
    }

    inner class Boid(x: Float, y: Float, r: Float): Circle(x, y, r) {
        private val maxSpeed = 0.3f
        private var location = Vector(x, y)
        private var velocity = Vector(0, 0)

        fun update(): Boid {

            if(r < maxRadius){
                r += 0.1f
            }

            var blackholeDirection = blackHole - location
            blackholeDirection.normalize()
            blackholeDirection *= 0.2f

            velocity += blackholeDirection
            location += velocity

            var closestDistance = Float.MAX_VALUE
            var closestIndex = -1

            if(boids.size == 1) return this

            boids.forEachIndexed { index, other ->
                if(other != this){
                    val distance = edgeDistance(other)
                    if(distance < closestDistance){
                        closestIndex = index
                        closestDistance = distance
                    }
                }

                var direction = location.direction(other.location)
                direction.normalize()
                direction *= (0.008f)/boids.size.toFloat()

                velocity += (direction)
                velocity.limit(maxSpeed)

            }

            val closest = boids[closestIndex]

            if(closestDistance < r) {
                var direction = location.direction(closest.location)
                direction.normalize()
                direction *= -0.4f

                velocity += (direction)
                velocity.limit(maxSpeed)

            }

            location += (velocity)

            if (this.location.x > width + r) this.location.x = -r
            if (this.location.x < -r) this.location.x = width.toFloat() + r
            if (this.location.y > height + r) this.location.y = -r
            if (this.location.y < -r) this.location.y = height.toFloat() + r

            x = location.x
            y = location.y

            return this
        }

        override fun draw(){
            noStroke()
            fill(boidColour, 0.05f)
            circle(location.x, location.y, r)

            fill(nucleusColour, 0.7f)
            circle(location.x, location.y, nucleusRadius)
        }
    }
}
