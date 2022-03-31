package examples.experiments

import coracle.*

class OrllewinDrawing: Drawing() {

    private var maxSpeed = 1.46f
    private var familyHopCycles = 250
    private val familyA = 0
    private val familyB = 1
    private val familyC = 2
    private val bodies = mutableListOf<Body>()
    private lateinit var wellA: Vector
    private lateinit var wellB: Vector
    private lateinit var wellC: Vector

    private var bodyColour = 0x205F77
    private var backgroundColour = 0xeeeae4

    override fun setup() {
        size(450, 350)
        wellA = Vector((width/6) * 1.5, height/2)
        wellB = Vector(width/2, height/2)
        wellC = Vector((width/6) * 4.5, height/2)
        val minBodies = 4
        val maxBodies = 8
        repeat(random(minBodies, maxBodies).toInt()) { bodies.add(Body(familyA)) }
        repeat(random(minBodies, maxBodies).toInt()) { bodies.add(Body(familyB)) }
        repeat(random(minBodies, maxBodies).toInt()) { bodies.add(Body(familyC)) }
    }

    override fun draw() {
        matchWindow()
        wellA.x = (width/6f) * 1.5f
        wellB.x = (width/2f)
        wellC.x = (width/6) * 4.5f

        wellA.y = height/2f
        wellB.y = height/2f
        wellC.y = height/2f
        background(backgroundColour)
        noStroke()

        bodies.forEach { body -> body.update().draw() }
    }

    inner class Body(var family: Int){
        private var velocity = Vector(0f, 0f)
        private var location: Vector = Vector.randomPosition(width, height)
        private var inFamilyCycles = 0

        fun update(): Body {
            val gravityWell = when (family) {
                familyA -> wellA
                familyB -> wellB
                familyC -> wellC
                else -> null
            }

            gravityWell?.let{
                var direction = gravityWell - location
                direction.normalize()
                direction *= 0.02f

                velocity += direction
                location += velocity
            }

            bodies.forEach { body ->
                if(body != this && body.family == family){
                    var bodyDirection = body.location - location
                    bodyDirection.normalize()
                    bodyDirection *= 0.01f

                    velocity += bodyDirection
                    velocity.limit(maxSpeed)
                    location += velocity
                }
            }

            if(inFamilyCycles > familyHopCycles){
                when (family) {
                    familyA -> {
                        if(random(100) < 1 || location.distance(wellB) < location.distance(wellA)){
                            this.family = familyB
                            inFamilyCycles = 0
                        }
                    }
                    familyB -> {
                        if(random(100) < 10 || location.distance(wellC) < location.distance(wellB)){
                            this.family = familyC
                            inFamilyCycles = 0
                        }else if(random(100) < 10 || location.distance(wellA) < location.distance(wellB)){
                            this.family = familyA
                            inFamilyCycles = 0
                        }
                    }
                    familyC -> {
                        if(random(100) < 1 || location.distance(wellB) < location.distance(wellC)){
                            this.family = familyB
                            inFamilyCycles = 0
                        }
                    }
                }
            }

            inFamilyCycles++

            return this
        }

        fun draw(){
            noStroke()

            fill(bodyColour, 0.2f)
            circle(location.x, location.y, 25)

            fill(bodyColour)
            circle(location.x, location.y, 4)
        }
    }
}