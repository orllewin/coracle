package drawings.experiments.boids.perlin_flow_field

import coracle.*
import kotlin.math.cos
import kotlin.math.sin

class PerlinNoiseDrawing2: Drawing() {

    val agents = mutableListOf<Agent>()
    val agentCount = 450
    var scale = 0.01f
    var speed = 1.2f
    var elapsed = 0

    override fun setup() {
        size(450, 450)

        repeat(agentCount){
            agents.add(Agent(random(width), random(height)))
        }
    }

    override fun draw() {
        stroke(0xffffff, 0.75f)

        agents.forEach { agent ->
            agent
                .avoidNearest()
                .updateFlowField()
                .checkBounds()
                .draw()
        }

        elapsed++
        if(elapsed > 350){
            Perlin.newSeed()
            scale = random(0.001f, 0.02f)
            speed = random(0.8f, 2f)
            elapsed = 0
        }

        foreground(0x000000, 0.035f)
    }

    inner class Agent(x: Float, y: Float): Vector(x, y) {

        var age = 0
        var deathAge = random(100, 340)

        fun updateFlowField(): Agent {
            if(frame % 3 != 0) return this
            age++
            val a = TAU * Perlin.noise(x * scale, y * scale)
            var direction = direction(Vector( x + (cos(a)).toFloat(), y + (sin(a) ).toFloat()))
            direction.normalize()
            direction *= 0.8f
            this.x += direction.x * speed
            this.y += direction.y * speed

            return this
        }

        fun avoidNearest(): Agent {
            var closestDistance = Float.MAX_VALUE
            var closestIndex = -1

            agents.forEachIndexed { index, other ->
                if(other != this){
                    val distance = distance(other)
                    if(distance < closestDistance){
                        closestIndex = index
                        closestDistance = distance
                    }
                }
            }

            val closest = agents[closestIndex]
            if(distance(closest) < 35) {
                var direction = direction(closest)
                direction.normalize()
                direction *= -0.3f

                this.x += direction.x
                this.y += direction.y
            }
            return this
        }

        fun checkBounds(): Agent {
            if(age >= deathAge || x < 0 || x > width || y < 0 || y > height ){
                x = random(width)
                y = random(height)
                age = 0
                deathAge = random(100, 340)
            }
            return this
        }
    }
}