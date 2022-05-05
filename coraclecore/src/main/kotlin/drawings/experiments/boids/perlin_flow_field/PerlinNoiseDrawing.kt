package drawings.experiments.boids.perlin_flow_field

import coracle.*
import kotlin.math.cos
import kotlin.math.sin

class PerlinNoiseDrawing: Drawing() {

    val agents = mutableListOf<Agent>()
    val agentCount = 2000
    var scale = 0.01f
    var speed = 0.4f
    var elapsed = 0

    override fun setup() {
        size(600, 400)

        repeat(agentCount){
            agents.add(Agent(random(width), random(height)))
        }
    }

    override fun draw() {
        matchWidth()
        stroke(0x000000, 0.5)
        repeat(agentCount){ i ->
            val agent = agents[i]
            agent.age++
            val a = 2 * TAU * Perlin.noise(agent.x * scale, agent.y * scale)
            agent.x += (cos(a)* speed).toFloat()
            agent.y += (sin(a)* speed).toFloat()
            if(agent.x < 0 || agent.x > width || agent.y < 0 || agent.y > height ){
                agent.x = random(width)
                agent.y = random(height)
            }
            point(agent.x, agent.y)
            agent.checkAge()
        }

        elapsed++
        if(elapsed > 150){
            Perlin.newSeed()
            scale = random(0.002f, 0.03f)
            speed = random(0.4f, 0.9f)
            elapsed = 0
        }

        foreground(0xf5f2f0, 0.09f)
    }

    inner class Agent(x: Float, y: Float): Vector(x, y) {

        var age = 0
        var deathAge = random(100, 2000)

        fun checkAge(){
            if(age >= deathAge){
                x = random(width)
                y = random(height)
                age = 0
                deathAge = random(50, 600)
            }
        }
    }
}