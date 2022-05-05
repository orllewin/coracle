package drawings.reference

import coracle.*
import kotlin.math.cos
import kotlin.math.sin

class ReferencePoint: Drawing() {

    val boids = mutableListOf<Boid>()
    val num = 1000
    var noiseScale = 0.01f
    var spd = 1f

    var t = 0
    var t2 = 0

    var m = 0f
    var m2 = 0f
    var mDir = 1

    val worldColour = 0xf5f2f0
    val boidColour = 0x000000

    var initied = false

    override fun setup() {
        size(450, 450)
    }

    override fun draw() {
        matchWidth()
        if(!initied){
            repeat(num){
                boids.add(Boid(random(width), random(height)))
            }
            initied = true
        }

        background(worldColour)
        stroke(boidColour, 0.5)
        repeat(num){ i ->
            val agent = boids[i]
            agent.move(i).updateTail().drawTail().checkBounds().checkAge()
        }

        m += 0.005f

        t++
        if(t > 150){
            Perlin.newSeed()
            noiseScale = random(0.01f, 0.05f)
            spd = random(1f, 2f)
            t = 0
        }

        t2++
        if(t2 >= 800){
            mDir *= -1
            t2 = 0
        }

        if(mDir == 1){
            m2 += 0.005f
        }else{
            m2 -= 0.005f
        }
    }

    inner class Boid(x: Float, y: Float): Vector(x, y) {

        var age = 0
        var deathAge = random(100, 2000)

        var tailLength = randomInt(10, 30)
        var tailX = FloatArray(tailLength)
        var tailY = FloatArray(tailLength)

        fun move(index: Int): Boid{
            val n = Perlin.noise(x * noiseScale + m, y * noiseScale * m2)
            val a = 2 * TAU * n
            val d = if(index % 2 == 0){
                1
            }else{
                -1
            }
            x += (cos(a)* spd * d).toFloat()
            y += (sin(a)* spd * d).toFloat()
            return this
        }

        fun checkBounds(): Boid{
            if(x < 0 || x > width || y < 0 || y > height ){
                x = random(width)
                y = random(height)
            }
            return this
        }

        fun updateTail(): Boid{
            val tailIndex = frame % tailLength
            tailX[tailIndex] = x
            tailY[tailIndex] = y

            return this
        }

        fun drawTail(): Boid{
            repeat(tailLength){ tailIndex ->
                stroke(boidColour, 0.4f)
                point(tailX[tailIndex], tailY[tailIndex])
            }

            return this
        }

        fun checkAge(): Boid{
            age++
            if(age >= deathAge){
                x = random(width)
                y = random(height)
                age = 0
                deathAge = random(50, 600)
            }

            return this
        }
    }
}