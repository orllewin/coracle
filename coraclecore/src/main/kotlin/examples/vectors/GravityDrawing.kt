package examples.vectors

import coracle.*

class GravityDrawing: Drawing() {

    private var maxSpeed = 2.2f
    private val bodies = mutableListOf<Body>()
    private lateinit var blackHole: Vector

    private var overlayColour = 0xBAE8CA
    private var bodyColour = 0x205F77
    private var backgroundColour = 0xecf8f0

    override fun setup() {
        size(450, 450)
        blackHole = Vector(width/2, height/2)
        repeat(random(3, 7).toInt()) { bodies.add(Body()) }
    }

    override fun draw() {
        matchWidth()
        background(backgroundColour)
        noStroke()

        bodies.forEach { body -> body.update().draw() }
    }

    inner class Body{
        private var velocity = Vector(0f, 0f)
        private var location: Vector = Vector.randomPosition(width, height)

        fun update(): Body{
            var blackholeDirection = blackHole - location
            blackholeDirection.normalize()
            blackholeDirection *= 0.06f

            velocity += blackholeDirection
            location += velocity

            bodies.forEach { body ->
                if(body != this){
                    var bodyDirection = body.location - location
                    bodyDirection.normalize()
                    bodyDirection *= 0.04f

                    velocity += bodyDirection

                    velocity.limit(maxSpeed)
                    location += velocity

                    //stroke(0xff0000, 0.15)
                    //line(location.x, location.y, body.location.x, body.location.y)
                }
            }

            return this
        }

        fun draw(){
            fill(bodyColour)
            noStroke()
            circle(location.x, location.y, 5)
        }
    }
}