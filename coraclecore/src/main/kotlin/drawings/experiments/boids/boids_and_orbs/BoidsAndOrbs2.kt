package drawings.experiments.boids.boids_and_orbs


import coracle.*
import coracle.shapes.Circle
import kotlin.math.cos
import kotlin.math.sin

class BoidsAndOrbs2: Drawing() {

    val minEpochLength = 650
    val maxEpochLength = 1800
    var epochLength = randomInt(minEpochLength, maxEpochLength)

    val minOrbSize = 3
    val maxOrbSize = 12
    val orbCount = 32
    val orbs = mutableListOf<Orb>()

    val spatialHash = SpatialHash(10, 10)
    val agentCount = 1400

    var scale = 0.01f
    var elapsed = 0

    override fun setup() {
        size(450, 450)

        repeat(orbCount){
            orbs.add(Orb(random(width), random(height)))
        }
        repeat(agentCount){
            spatialHash.add(Agent(random(width), random(height)))
        }
    }

    override fun draw() {
        fill(0xffffff, 0.2f)
        noStroke()
        orbs.forEach { orb ->
            orb.updateFlowField().grow().checkBounds().draw()
        }

        stroke(0xffffff, 0.75f)
        spatialHash
            .iterate()
            .remap()

        elapsed++
        if(elapsed > epochLength){
            Perlin.newSeed()
            scale = random(0.008f, 0.02f)
            epochLength = randomInt(minEpochLength, maxEpochLength)
            elapsed = 0
        }

        foreground(0x000000, 0.085f)
    }

    inner class SpatialHash(private val columns: Int, private val rows: Int){
        val cellPopulations = HashMap<Int, MutableList<Agent>>()
        var initialised = false

        init {
            var index = -1
            repeat(rows * columns){
                index++
                cellPopulations[index] = mutableListOf()
            }
        }

        fun add(c: Agent): SpatialHash {
            val index = getIndexHash(c.x.toInt(), c.y.toInt())
            val population = cellPopulations[index]
            population?.add(c)

            initialised = true
            return this
        }

        fun getIndexHash(x: Int, y: Int): Int {
            val col = (x * columns / (width + 1))
            val row = (y * rows / (height + 1))
            return row * columns + col
        }

        fun iterate(): SpatialHash {
            cellPopulations.forEach { cellCollection ->
                cellCollection.value.forEach { agent ->
                    agent
                        .avoidOrbs()
                        .updateFlowField()
                        .checkBounds()
                        .draw()
                }
            }

            return this
        }

        fun remap(){
            cellPopulations.values.forEachIndexed { index, agents ->
                agents.indices.reversed().forEach { i ->
                    val agent = agents[i]
                    val correctIndex = getIndexHash(agent.x.toInt(), agent.y.toInt())
                    if(correctIndex != index){
                        agents.removeAt(i)
                        add(agent)
                    }
                }
            }
        }
    }

    inner class Orb(x: Float, y: Float): Circle(x, y, 1){

        var size = 1f
        var targetSize = random(minOrbSize, maxOrbSize)
        var growthRate = random(0.08f, 0.4f)
        val orbSpeed = 0.3f

        var age = 0
        var deathAge = randomInt(300, 800)

        fun updateFlowField(): Orb {
            val a = TAU * Perlin.noise(x * scale, y * scale)
            var direction = Vector(x, y).direction(Vector( x + (cos(a)).toFloat(), y + (sin(a) ).toFloat()))
            direction *= 0.4f
            x += direction.x * orbSpeed
            y += direction.y * orbSpeed
            return this
        }

        fun grow(): Orb {

            if(age < deathAge){
                if(size < targetSize){
                    size += growthRate
                }

            }else{
                if(size > 1){
                    size -= 0.1f
                }else{
                    respawn()
                }
            }

            r = size
            age++

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
            growthRate = random(0.08f, 0.4f)
            age = 0
            deathAge = randomInt(100, 400)
        }
    }

    inner class Agent(x: Float, y: Float): Vector(x, y) {

        var age = 0
        var deathAge = random(100, 340)
        private var velocity = Vector(0f, 0f)
        private var maxSpeed = random(2.2f, 3f)

        fun updateFlowField(): Agent {
            if(frame % 3 != 0) return this
            age++
            val a = TAU * Perlin.noise(x * scale, y * scale)
            val direction = direction(Vector( x + (cos(a)).toFloat(), y + (sin(a) ).toFloat()))
            velocity += direction
            velocity.limit(maxSpeed)
            this.x += velocity.x
            this.y += velocity.y

            return this
        }

        fun avoidOrbs(): Agent {
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

        fun checkBounds(): Agent {
            if(age >= deathAge || x < 0 || x > width || y < 0 || y > height ){
                x = random(width)
                y = random(height)
                age = 0
                deathAge = random(80, 200)
            }
            return this
        }
    }
}