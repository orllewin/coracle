package drawings.articles.foreground_versus_tails

import coracle.*

/*
    This is just a slightly modified version of the TadpolesTails drawing
 */
class Tails: Drawing() {
    private var worldColour = 0xf5f2f0
    private val boidColour = 0x000000
    private val tadpoles = mutableListOf<Tadpole>()
    private val cycleOffspring = mutableListOf<Tadpole>()
    private val touchOffspring = mutableListOf<Tadpole>()
    private val maxRelationshipMemory = 10
    private val count = 30
    private val maxPopulation = 50
    private var offspring = 0
    private var maxSize = 6f
    private var tadpoleColour = 0x000000

    override fun setup() {
        size(350, 350)
        repeat(count){ index ->
            tadpoles.add(Tadpole(index))
        }
    }

    override fun draw() {
        background(worldColour)

        tadpoles.forEach { tadpole ->
            tadpole.update().draw()
        }

        tadpoles.removeAll { tadpole ->
            !tadpole.alive
        }

        when (tadpoles.size) {
            1 -> {
                repeat(count){ index ->
                    tadpoles.add(Tadpole(index))
                }
            }
        }

        tadpoles.addAll(cycleOffspring)
        cycleOffspring.clear()
        tadpoles.addAll(touchOffspring)
        touchOffspring.clear()
    }

    inner class Tadpole(private val id: Int, spawnLocation: Vector?, private val parentId: Int?){

        constructor(id: Int) : this(id, null, null)

        private var location: Vector = when {
            spawnLocation != null -> spawnLocation
            else -> Vector(random(width), random(height))
        }

        private var velocity = Vector(0f, 0f)
        private var acceleration: Vector? = null
        private var maxSpeed = 3.5f
        private var relationshipLength = random(400, 900)
        private var allowedCycles = random(900, 2000).toInt()
        private var closestDistance = Float.MAX_VALUE
        private val exes = mutableListOf<Int>()
        private var currentCompanion = -1
        private var cycles = 0
        private var cyclesAttached = 0
        private var inRelationship = false
        private var hasOffspring = false
        var alive = true

        var tailLength = randomInt(10, 30)
        var tailX = FloatArray(tailLength)
        var tailY = FloatArray(tailLength)

        fun update(): Tadpole {
            if(tadpoles.size == 1) return this
            cycles++
            if(cycles == allowedCycles){
                //die
                alive = false
            }

            val distances = HashMap<Int, Float>()
            tadpoles.forEach { tadpole ->
                val distance = location.distance(tadpole.location)
                distances[tadpole.id] = distance
            }

            //Sort - self will be index 0
            val sortedDistances = distances.toList().sortedBy { (_, value) -> value}

            //Closest neighbour
            val closestTadpole = tadpoles.find { tadpole -> tadpole.id == sortedDistances[1].first }

            closestDistance = sortedDistances[1].second

            var directionToTadpole = closestTadpole!!.location - location
            directionToTadpole.normalize()

            //Is the closest tadpole the same as last cycle?
            if(currentCompanion == closestTadpole.id && closestDistance < 2.5){
                cyclesAttached++
            }else{
                //This is more correct for what we're trying to achieve, but the result is more boring movement,
                //so remove it:
                //cyclesAttached = 0
            }

            inRelationship = cyclesAttached > 100

            //Update closest tadpole id after we've checked if they were previous closest
            currentCompanion = closestTadpole.id

            //Have they been together too long?
            when {
                cyclesAttached > relationshipLength -> {
                    exes.add(currentCompanion)
                    currentCompanion = -1
                    cyclesAttached = 0
                    relationshipLength = random(100, 500)
                    inRelationship = false
                }
            }

            //If closest neighbour is an ex then move away,
            //if it's a parent or siblings move away quickly,
            //otherwise stay close to current companion
            directionToTadpole *= when {
                exes.contains(closestTadpole.id) -> -0.6f
                (parentId != null && (parentId == closestTadpole.id)) -> -2.4f
                (parentId != null && (parentId == closestTadpole.parentId)) -> -2.4f
                else -> 0.2f
            }

            acceleration = directionToTadpole

            //Block only applies to tadpoles in a relationship
            if(inRelationship){
                //Find nearest single tadpole, index 0 is self, index 1 is partner
                var foundThreat = false
                for(index in 2 until tadpoles.size){
                    val tadpole = tadpoles.find { tadpole ->
                        tadpole.id == sortedDistances[index].first
                    }

                    if(!tadpole!!.inRelationship && !foundThreat){
                        //Single - is a threat, move away
                        val directionToThreat = tadpole.location - location
                        directionToThreat.normalise()
                        acceleration = directionToTadpole + (directionToThreat * -0.4f)
                        foundThreat = true
                    }

                    if(foundThreat){
                        break
                    }
                }

                //Arbitary max population count
                if(!hasOffspring && cyclesAttached > relationshipLength/2 && tadpoles.size < maxPopulation){
                    if(random(100) < 8){
                        hasOffspring = true
                        val numberOfOffspring = random(1, 2).toInt()
                        repeat(numberOfOffspring){
                            cycleOffspring.add(Tadpole(count + offspring, location, id))
                        }

                        offspring += numberOfOffspring
                    }
                }
            }

            velocity += acceleration!!

            if(inRelationship){
                val blackHole = Vector(width/2, height/2)
                var directionToBlackHole = blackHole - location
                directionToBlackHole.normalize()
                directionToBlackHole *= 0.3f
                velocity += directionToBlackHole

                velocity.limit(maxSpeed / 1.25f)
            }else{
                velocity.limit(maxSpeed)
            }

            location += velocity

            val tailIndex = frame % tailLength
            tailX[tailIndex] = location.x
            tailY[tailIndex] = location.y

            if (exes.size == maxRelationshipMemory) exes.removeAt(0)//Forget oldest relationships
            return this
        }

        fun draw() {
            repeat(tailLength){ tailIndex ->
                stroke(boidColour, 0.4f)
                point(tailX[tailIndex], tailY[tailIndex])
            }

            val diam = if(cycles < allowedCycles/2){
                Math.map(cycles.toFloat(), 0f, allowedCycles.toFloat(), 2f, maxSize)
            }else{
                Math.map(cycles.toFloat(), 0f, allowedCycles.toFloat(), maxSize, 2f)
            }
            checkBounds(diam)
            fill(tadpoleColour)

            circle(location.x, location.y, diam.toInt())
        }

        private fun checkBounds(diam: Float) {
            when {
                location.x > width +diam -> location.x = -diam
                location.x < -diam -> location.x = width.toFloat() + diam
            }
            when {
                location.y > (height +diam) -> location.y = -diam
                location.y < -diam -> location.y = height.toFloat() + diam
            }
        }
    }
}