package coracle

import coracle.shapes.Circle
import coracle.shapes.Point
import kotlin.math.sqrt

open class Vector(var x: Float, var y: Float) {

    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())
    constructor(p: Point) : this(p.x, p.y)
    constructor() : this(0f, 0f)

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

    fun distance(oX: Float, oY: Float): Float{
        val dx = x -oX
        val dy = y - oY
        return sqrt(dx * dx + dy * dy)
    }

    fun distance(other: Vector): Float = distance(other.x, other.y)
    fun distance(p: Point): Float = distance(p.x, p.y)
    fun distance(c: Circle): Float = distance(c.x, c.y)

    fun direction(other: Vector): Vector {
        val direction = Vector(other.x - x, other.y - y)
        direction.normalise()
        return direction
    }

    fun direction(p: Point): Vector = direction(Vector(p.x, p.y))
    fun direction(c: Circle): Vector = direction(Vector(c.x, c.y))

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

    open fun draw(){
        Easel.drawing?.point(x, y)
    }
}