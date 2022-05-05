package drawings.experiments.boids

import coracle.*
import kotlin.math.cos
import kotlin.math.sin

class BasicTails: Drawing() {
    val w = 450
    val h = 450
    val boids = Array(350){ Boid(random(w), random(h))}
    val boidColour = 0x000000
    val worldColour = 0xf5f2f0

    var epochElapsed = 0
    var epochLength = 800

    var minNoiseScale = 0.025f
    var maxNoiseScale = 0.06f
    var noiseScale = random(minNoiseScale, maxNoiseScale)

    override fun setup() {
        size(w, h)

        stroke(boidColour)
        fill(boidColour, 0.85f)
    }

    override fun draw() {
        background(worldColour)

        boids.forEach { boid ->
            boid
                .iterate()
                .checkBounds()
                .draw()
        }

        epochElapsed++
        if(epochElapsed >= epochLength){
            Perlin.newSeed()
            noiseScale = random(minNoiseScale, maxNoiseScale)
            epochElapsed = 0
        }
    }

    inner class Boid(x: Float, y: Float): Vector(x, y) {

        var age = 0
        var deathAge = random(100, 340)

        var velocity = Vector(0f, 0f)
        var maxSpeed = 1.7f

        var tailLength = randomInt(10, 30)
        var tailX = FloatArray(tailLength)
        var tailY = FloatArray(tailLength)

        fun iterate(): Boid {
            val a = TAU * Perlin.noise(x * noiseScale, y * noiseScale)
            var direction = direction(Vector( x + (cos(a)).toFloat(), y + (sin(a) ).toFloat()))
            direction *= 0.35f

            velocity += direction

            velocity.limit(maxSpeed)

            x += velocity.x
            y += velocity.y

            val tailIndex = frame % tailLength
            tailX[tailIndex] = x
            tailY[tailIndex] = y

            age++

            return this
        }

        override fun draw(){
            repeat(tailLength){ tailIndex ->
                stroke(boidColour, 0.4f)
                point(tailX[tailIndex], tailY[tailIndex])

                if(tailIndex == tailLength - 1){
                    noStroke()
                    circle(x, y, 1)
                }
            }

        }

        fun checkBounds(): Boid {
            if(age >= deathAge || x < 0 || x > width || y < 0 || y > height ){
                x = random(width)
                y = random(height)
                tailLength = randomInt(10, 30)
                tailX = FloatArray(tailLength)
                tailY = FloatArray(tailLength)
                age = 0
                deathAge = random(100, 340)
            }
            return this
        }
    }
}