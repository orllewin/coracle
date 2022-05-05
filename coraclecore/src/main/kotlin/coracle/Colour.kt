package coracle

import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

typealias Color = Colour
class Colour(var c: Int) {

    constructor(r: Int, g: Int, b: Int): this((r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff))
    constructor(r: Float, g: Float, b: Float): this((r.toInt() and 0xff shl 16) or (g.toInt() and 0xff shl 8) or (b.toInt() and 0xff))

    var r: Int = 0
    var g: Int = 0
    var b: Int = 0
    var a: Int = 255

    init {
        r = (c shr 16) and 0xFF
        g = (c shr 8) and 0xFF
        b = (c shr 0) and 0xFF
    }

    fun toHexString(): String{
        return "#${c.toString(16)}"
    }

    fun brighten(){
        r = min(255, r + 1)
        g = min(255, g + 1)
        b = min(255, b + 1)
        c = (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }

    fun darken(){
        r = max(0, r - 1)
        g = max(0, g - 1)
        b = max(0, b - 1)
        c = (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }

    fun randomise(){
        r = randomInt(0, 255)
        g = randomInt(0, 255)
        b = randomInt(0, 255)
        c = (r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff)
    }

    /*
        Returns value in range 0 to 255
     */
    fun brightness(): Int{
        return sqrt(0.241 * r * r + 0.691 * g * g + 0.068 * b * b).toInt()
    }

    companion object{
        fun lerp(a: Colour, b: Colour, t: Float): Colour {
            return Colour((a.r + (b.r - a.r) * t).toInt(), (a.g + (b.g - a.g) * t).toInt(), (a.b + (b.b - a.b) * t).toInt())
        }

        fun random(): Colour = Colour(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255))

        fun red(colour: Int): Int = (colour shr 16) and 0xFF
        fun green(colour: Int): Int = (colour shr 8) and 0xFF
        fun blue(colour: Int): Int = (colour shr 0) and 0xFF

        fun grey(grey: Int): Int = Colour(grey, grey, grey).c
    }
}