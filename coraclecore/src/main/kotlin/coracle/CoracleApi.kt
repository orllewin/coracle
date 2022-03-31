package coracle

import kotlin.random.Random

fun color(r: Int, g: Int, b: Int): Int {
    var colour: Int = r
    colour = (colour shl 8) + g
    colour = (colour shl 8) + b
    return colour
}

fun color(r: Float, g: Float, b: Float): Int = color(r.toInt(), g.toInt(), b.toInt())

fun red(colour: Int): Int = (colour shr 16) and 0xFF
fun green(colour: Int): Int = (colour shr 8) and 0xFF
fun blue(colour: Int): Int = (colour shr 0) and 0xFF

fun lerpColor(startColor: Int, endColor: Int, amount: Float): Int{
    val sR = red(startColor)
    val sG = green(startColor)
    val sB = blue(startColor)
    val eR = red(endColor)
    val eG = green(endColor)
    val eB = blue(endColor)
    val r = lerpA(sR.toFloat(), eR.toFloat(), amount)
    val g = lerpA(sG.toFloat(), eG.toFloat(), amount)
    val b = lerpA(sB.toFloat(), eB.toFloat(), amount)
    return color(r.toInt(), g.toInt(), b.toInt())
}


//Precise
fun lerpA(start: Float, end: Float, amount: Float): Float {
    return start * (1-amount) + end * amount
}

//Less precise
fun lerpB(a: Float, b: Float, i: Float): Float{
    return a + i * (b - a)
}

fun map(value: Float, start1: Float, stop1: Float, start2: Float, stop2: Float): Float =
    start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))

fun mapInt(value: Int, start1: Int, stop1: Int, start2: Int, stop2: Int): Float =
    map(value.toFloat(), start1.toFloat(), stop1.toFloat(), start2.toFloat(), stop2.toFloat())

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

/*
    Ring Buffer indexing: If index -1 is requested for size 8 this will return the last index at 7
    (If you have points plotted in a circle you can get neighbours by using current index -1 and +1)
 */
fun posMod(index: Int, size: Int): Int = ((index % size) + size) % size
