package drawings.experiments.boids

import coracle.*
import coracle.shapes.Circle
import kotlin.math.cos
import kotlin.math.sin

class TailsAndOrbs2Mobile: Drawing() {

    val minOrbSize = 6
    val maxOrbSize = 24
    lateinit var orbs: Array<Orb>
    lateinit var  boids: Array<Boid>
    val boidColour = 0x000000
    val worldColour = 0xffffff
    val orbColour = 0xe5e2e0

    var epochElapsed = 0
    var epochLength = 800

    var minNoiseScale = 0.025f
    var maxNoiseScale = 0.06f
    var noiseScale = random(minNoiseScale, maxNoiseScale)

    var inited = false

    override fun setup() {
        stroke(boidColour)
        noFill()
    }

    override fun draw() {
        matchWindow()
        if(!inited){
            orbs = Array(24){ Orb(random(width), random(height))}
            boids = Array(350){ Boid(random(width), random(height))}
            inited = true
        }
        background(worldColour)

        orbs.forEach { orb ->
            orb.updateFlowField().grow().checkBounds().draw()
        }

        boids.forEach { boid ->
            boid
                .iterate()
                .avoidOrbs()
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

        fun avoidOrbs(): Boid {
            orbs.forEach { orb ->
                if(distance(orb) < orb.r + 10){
                    var direction = direction(orb)
                    direction *= -0.8f
                    velocity += direction
                    velocity.limit(maxSpeed)
                    this.x += velocity.x
                    this.y += velocity.y
                }
            }
            return this
        }

        override fun draw(){
            repeat(tailLength){ tailIndex ->
                stroke(boidColour, 0.4f)
                point(tailX[tailIndex], tailY[tailIndex])

                if(tailIndex == tailLength - 1){
                    noStroke()
                    fill(boidColour)
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

    inner class Orb(x: Float, y: Float): Circle(x, y, 1){

        var size = 1f
        var targetSize = random(minOrbSize, maxOrbSize)
        var growthRate = 0.6f

        var velocity = Vector(0f, 0f)
        var maxSpeed = 0.25f

        var age = 0
        var deathAge = randomInt(300, 800)

        val count = randomInt(8, 32)
        var rotate = 0f
        var rotateDirection = when {
            random(100) < 50 -> -1
            else -> 1
        }

        fun updateFlowField(): Orb {
            val a = TAU * Perlin.noise(x * noiseScale, y * noiseScale)
            var direction = Vector(x, y).direction(Vector( x + (cos(a)).toFloat(), y + (sin(a) ).toFloat()))
            direction *= 0.4f

            velocity += direction

            velocity.limit(maxSpeed)

            x += velocity.x
            y += velocity.y

            return this
        }

        fun grow(): Orb {

            if(age < deathAge){
                if(size < targetSize){
                    size += growthRate
                }

            }else{
                if(size > 1){
                    size -= growthRate
                }else{
                    respawn()
                }
            }

            r = size
            age++

            when (rotateDirection) {
                1 -> rotate += 0.01f
                else -> rotate -= 0.01f
            }

            return this
        }

        fun checkBounds(): Orb {
            if(x < 0 - r || x > width + r || y < 0 - r || y > height + r ){
                respawn()
            }
            return this
        }

        private fun respawn(){
            x = random(width)
            y = random(height)
            targetSize = random(minOrbSize, maxOrbSize)
            size = 1f
            r = size
            age = 0
            deathAge = randomInt(100, 400)
        }

        val parametricDraw = true

        override fun draw(){
            when {
                parametricDraw -> parametricDraw()
                else -> {
                    fill(orbColour)
                    stroke(boidColour, 0.8f)
                    circle(x, y, r)
                }
            }
        }

        private fun parametricDraw(){
            stroke(boidColour)
            repeat(count){ i ->
                val angle = (i * TWO_PI / count).toFloat()
                point(x + cos(angle + rotate) * r, y + sin(angle + rotate) * r)
            }
        }
    }
}