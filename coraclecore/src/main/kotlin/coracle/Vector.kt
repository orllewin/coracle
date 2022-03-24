package coracle

import kotlin.math.sqrt

data class Vector(var x: Float, var y: Float) {

    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())
    constructor(coord: Coord) : this(coord.x, coord.y)

    companion object{
        fun dot(v1: Vector, v2: Vector): Float = v1.x * v2.x + v1.y * v2.y

        fun randomDirection(): Vector {
            val direction = Vector(random(-10f, 10f), random(-10f, 10f))
            direction.normalise()
            return direction
        }

        fun randomPosition(width: Number, height: Number): Vector = Vector(random(0f, width), random(0f, height))

        fun direction(start: Vector, end: Vector): Vector = Vector(
            end.x - start.x,
            end.y - start.y
        )

        fun normal(vectorA: Vector, vectorB: Vector): Vector {
            val delta = vectorA - vectorB
            delta.normalize()
            return Vector(-delta.y, delta.x)
        }

        fun empty(): Vector = Vector(0, 0)
    }

    fun reset(){
        x = 0f
        y = 0f
    }

    fun magnitude(): Float = sqrt(x * x + y * y)

    fun normalise(){
        val magnitude = magnitude()
        if(magnitude > 0){
            this.x = x/magnitude
            this.y = y/magnitude
        }
    }

    fun distance(other: Vector): Float{
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    fun direction(other: Vector): Vector {
        val direction = Vector(other.x - x, other.y - y)
        direction.normalise()
        return direction
    }

    fun dot(other: Vector): Float = x * other.x + y * other.y

    fun perpendicular(): Vector {
        val pX = -y
        val pY = x
        return Vector(pX, pY)
    }

    fun normalize() = normalise()

    fun limit(max: Float){
        when {
            magnitudeSquared() > max * max -> {
                normalise()
                x *= max
                y *= max
            }
        }
    }

    fun magnitudeSquared(): Float = x * x + y * y

    fun coord(): Coord = Coord(x, y)

    operator fun plus(vector: Vector): Vector = Vector(
        this.x + vector.x,
        this.y + vector.y
    )

    operator fun minus(vector: Vector): Vector = Vector(
        this.x - vector.x,
        this.y - vector.y
    )

    operator fun times(value: Number): Vector = Vector(
        this.x * value.toFloat(),
        this.y * value.toFloat()
    )

    operator fun div(value: Number): Vector = Vector(
        this.x / value.toFloat(),
        this.y / value.toFloat()
    )

    fun clone(): Vector = Vector(x, y)
}