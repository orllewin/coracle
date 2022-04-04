package coracle

import kotlin.random.Random

const val PI = 3.1415927
const val TWO_PI = 6.2831855
const val TAU = 6.2831855
const val QUARTER_PI = 0.7853982

typealias Degree = Double
typealias Radian = Double

object Math {

    fun Degree.rad(): Radian = this / 180 * PI
    fun Radian.deg(): Degree = this * 180 / PI
    fun Int.deg(): Degree = this.toDouble() * 180 / PI

    fun randomDegree(): Degree {
        return random(0.0, 360.0).toDouble()
    }

    fun lerp(start: Float, end: Float, amount: Float): Float {
        return start * (1-amount) + end * amount
    }

    fun map(value: Float, start1: Float, stop1: Float, start2: Float, stop2: Float): Float =
        start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))

    fun mapInt(value: Int, start1: Int, stop1: Int, start2: Int, stop2: Int): Int =
        map(value.toFloat(), start1.toFloat(), stop1.toFloat(), start2.toFloat(), stop2.toFloat()).toInt()

    /*
        Ring Buffer indexing: If index -1 is requested for size 8 this will return the last index at 7
        (If you have points plotted in a circle you can get neighbours by using current index -1 and +1)
     */
    fun posMod(index: Int, size: Int): Int = ((index % size) + size) % size
}

fun random(start: Number, end: Number): Float = Random.nextLong(start.toLong(), end.toLong()).toFloat()
fun random(start: Float, end: Float): Float = Random.nextDouble(start.toDouble(), end.toDouble()).toFloat()
fun random(number: Number): Float{
    if(number.toFloat() <= 0) return 0F
    return Random.nextLong(0L, number.toLong()).toFloat()
}
fun randomInt(start: Number, end: Number): Int = Random.nextLong(start.toLong(), end.toLong()).toInt()

fun randomInt(number: Number): Int{
    if(number.toFloat() <= 0) return 0
    return Random.nextLong(0L, number.toLong()).toInt()
}