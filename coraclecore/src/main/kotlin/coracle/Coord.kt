package coracle

import kotlin.math.sqrt

@Deprecated("Use Point instead")
data class Coord(var x: Float, var y: Float){

    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())
    constructor(x: Double, y: Double) : this(x.toFloat(), y.toFloat())

    fun dist(coord: Coord): Float {
        val dx = this.x - coord.x
        val dy = this.y - coord.y
        return sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

    fun set(x: Number, y: Number){
        this.x = x.toFloat()
        this.y = y.toFloat()
    }

    fun toVector(): Vector = Vector(x, y)
}