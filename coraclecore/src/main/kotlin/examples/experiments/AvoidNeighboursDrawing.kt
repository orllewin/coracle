package examples.experiments

import coracle.Drawing
import coracle.Vector

class AvoidNeighboursDrawing: Drawing() {

    val motes = mutableListOf<Mote>()
    var cellWidth = 30f

    override fun setup() {
        size(450, 450)
        repeat(120) { motes.add(Mote()) }
    }

    override fun draw() {
        matchWidth()
        background(DEFAULT_BACKGROUND)
        motes.forEach { mote -> mote.update().draw() }
    }

    inner class Mote{

        private val maxSpeed = 1.5f
        private var location = Vector.randomPosition(width, height)
        private var velocity = Vector(0, 0)

        fun update(): Mote {

            var closestDistance = Float.MAX_VALUE
            var closestIndex = -1

            motes.forEachIndexed { index, other ->
                if(other != this){
                    val distance = location.distance(other.location)
                    if(distance < closestDistance){
                        closestIndex = index
                        closestDistance = distance
                    }
                }
            }

            val closest = motes[closestIndex]
            var direction = location.direction(closest.location)
            direction.normalize()
            direction *= -0.2f

            velocity += (direction)
            velocity.limit(maxSpeed)
            location += (velocity)

            if (this.location.x > width + cellWidth) this.location.x = -cellWidth
            if (this.location.x < -cellWidth) this.location.x = width.toFloat() + cellWidth
            if (this.location.y > height + cellWidth) this.location.y = -cellWidth
            if (this.location.y < -cellWidth) this.location.y = height.toFloat() + cellWidth

            return this
        }

        fun draw(){
            noStroke()
            fill(DEFAULT_FOREGROUND, 0.15f)
            circle(location.x, location.y, (cellWidth/2).toInt())

            fill(DEFAULT_FOREGROUND)
            circle(location.x, location.y, 4)
        }
    }
}