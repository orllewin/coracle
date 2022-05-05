package drawings.experiments.boids

import coracle.Drawing
import coracle.Vector
import coracle.random
import coracle.shapes.Circle
import coracle.shapes.Point

class FallingRain: Drawing() {

    val populationCount = 2000
    val bodyCount = 20

    var inited = false

    val boids = mutableListOf<Boid>()
    val bodies = mutableListOf<Body>()

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        matchWindow()

        if(!inited){
            repeat(bodyCount){
                bodies.add(Body(random(width), random(height), random(15, 40).toInt()))
            }
            repeat(populationCount){
                boids.add(Boid(randomPoint()))
            }
            inited = true
        }
        noStroke()
        fill(0x44aa66)
        bodies.forEach { body ->
            body.draw()
        }

        stroke(0xffffff)
        boids.forEach { boid ->
            boid
                .iterate()
                .avoidBodies()
                .checkBounds()
                .draw()
        }

        foreground(0x000000, 0.1f)
    }

    inner class Body(x: Float, y: Float, r: Int): Circle(x, y, r)

    inner class Boid(p: Point): Vector(p){

        var age = 0
        var deathAge = random(100, 340)

        private var velocity = Vector(0f, 0f)
        private var maxSpeed = 2f

        fun iterate(): Boid {
            var direction = direction(Vector(x, height))
            direction *= 0.1f

            velocity += direction

            velocity.limit(maxSpeed)

            this.x += velocity.x
            this.y += velocity.y

            age++

            return this
        }

        fun avoidBodies(): Boid {
            bodies.forEach { body ->
                if(distance(body) < body.r + 10){
                    var direction = direction(body)
                    direction *= -0.2f
                    velocity += direction
                    velocity.limit(maxSpeed/2)
                    this.x += velocity.x
                    this.y += velocity.y
                }
            }

            return this
        }

        fun checkBounds(): Boid {
            if(age >= deathAge || x < 0 || x > width || y < 0 || y > height ){
                val respawn = randomPoint()
                x = respawn.x
                y = respawn.y
                age = 0
                deathAge = random(100, 340)
            }
            return this
        }
    }

    private fun randomPoint(): Point {
        var invalid = true
        var point = Point(random(width), random(height))

        while(invalid){
            val body = bodies.find { body ->
                body.contains(point)
            }

            if(body == null){
                invalid = false
            }else{
                point = Point(random(width), random(height))
            }
        }

        return point
    }
}