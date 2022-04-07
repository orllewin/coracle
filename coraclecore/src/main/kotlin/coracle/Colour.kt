package coracle

import kotlin.math.max
import kotlin.math.min

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

    fun brighter(): Colour{
        val brighter =  Colour(min(255, (this.r * 1.2).toInt()), min(255, (this.g * 1.2).toInt()), min(255, (this.b * 1.2).toInt()))
        brighter.a = this.a
        return brighter
    }

    fun darker(): Colour{
        val darker =  Colour(max(0, (this.r * 0.8).toInt()), max(0, (this.g * 0.8).toInt()), max(0, (this.b * 0.8).toInt()))
        darker.a = this.a
        return darker
    }

    companion object{
        fun lerp(a: Colour, b: Colour, t: Float): Colour {
            return Colour((a.r + (b.r - a.r) * t).toInt(), (a.g + (b.g - a.g) * t).toInt(), (a.b + (b.b - a.b) * t).toInt())
        }

        fun random(): Colour = Colour(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255))

        fun red(colour: Int): Int = (colour shr 16) and 0xFF
        fun green(colour: Int): Int = (colour shr 8) and 0xFF
        fun blue(colour: Int): Int = (colour shr 0) and 0xFF
    }
}