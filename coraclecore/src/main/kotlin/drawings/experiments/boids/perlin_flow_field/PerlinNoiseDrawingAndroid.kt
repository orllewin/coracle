package drawings.experiments.boids.perlin_flow_field

import coracle.*
import kotlin.math.cos
import kotlin.math.sin

class PerlinNoiseDrawingAndroid: Drawing() {
    val spatialHash = SpatialHash(12, 12)
    val agentCount = 400
    var scale = 0.01f
    var speed = 0.8f
    var elapsed = 0

    override fun setup() {
        size(450, 400)
        repeat(agentCount){
            spatialHash.add(Agent(random(width), random(height)))
        }
        stroke(0xffffff, 0.4f)
    }

    override fun draw() {
        background(0x1d1d1d)

        spatialHash
            .iterate()
            .remap()

        elapsed++
        if(elapsed > 350){
            Perlin.newSeed()
            scale = random(0.001f, 0.02f)
            speed = random(0.8f, 2f)
            elapsed = 0
        }
    }

    inner class SpatialHash(private val columns: Int, private val rows: Int){
        val cellPopulations = HashMap<Int, MutableList<Agent>>()
        val cellWidth: Int = width/columns
        val cellHeight: Int = height/rows

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
                        .avoidNearest()
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

    inner class Agent(x: Float, y: Float): Vector(x, y) {

        var age = 0
        var deathAge = random(100, 340)

        var tailLength = randomInt(20, 40)
        var tailX = FloatArray(tailLength)
        var tailY = FloatArray(tailLength)

        fun updateFlowField(): Agent {
            if(frame % 3 != 0) return this
            age++
            val a = TAU * Perlin.noise(x * scale, y * scale)
            var direction = direction(Vector( x + (cos(a)).toFloat(), y + (sin(a) ).toFloat()))
            direction.normalize()
            direction *= 0.8f
            this.x += direction.x * speed
            this.y += direction.y * speed

            if(frame % 3 == 0) {
                val tailIndex = frame % tailLength
                tailX[tailIndex] = x
                tailY[tailIndex] = y
            }

            return this
        }

        fun getNeighbourhoodAgents(): List<Agent>{
            val neighbours = mutableListOf<Agent>()
            val thisIndex = spatialHash.getIndexHash(this.x.toInt(), this.y.toInt())
            val leftIndex = spatialHash.getIndexHash(this.x.toInt() - spatialHash.cellWidth, this.y.toInt())
            val rightIndex = spatialHash.getIndexHash(this.x.toInt() + spatialHash.cellWidth, this.y.toInt())
            val topIndex = spatialHash.getIndexHash(this.x.toInt(), this.y.toInt() - spatialHash.cellHeight)
            val bottomIndex = spatialHash.getIndexHash(this.x.toInt(), this.y.toInt() + spatialHash.cellHeight)
            neighbours.addAll(spatialHash.cellPopulations[thisIndex] ?: listOf())
            neighbours.addAll(spatialHash.cellPopulations[leftIndex] ?: listOf())
            neighbours.addAll(spatialHash.cellPopulations[rightIndex] ?: listOf())
            neighbours.addAll(spatialHash.cellPopulations[topIndex] ?: listOf())
            neighbours.addAll(spatialHash.cellPopulations[bottomIndex] ?: listOf())

            return neighbours
        }

        fun avoidNearest(): Agent {
            var closestDistance = Float.MAX_VALUE
            var closestIndex = -1

            val agents = getNeighbourhoodAgents()
            agents.forEachIndexed { index, other ->
                if(other != this){
                    val distance = distance(other)
                    if(distance < closestDistance){
                        closestIndex = index
                        closestDistance = distance
                    }
                }
            }

            if(closestIndex != -1){
                val closest = agents[closestIndex]
                if(distance(closest) < 35) {
                    var direction = direction(closest)
                    direction.normalize()
                    direction *= -0.3f

                    this.x += direction.x
                    this.y += direction.y
                }
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

        override fun draw() {

            repeat(tailLength){ tailIndex ->
                point(tailX[tailIndex], tailY[tailIndex])
            }
        }
    }
}