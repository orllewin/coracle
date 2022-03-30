package coracle

typealias Color = Colour
class Colour(val c: Int) {

    constructor(r: Int, g: Int, b: Int): this((r and 0xff shl 16) or (g and 0xff shl 8) or (b and 0xff))

    var r: Int = 0
    var g: Int = 0
    var b: Int = 0
    var a: Int = 255

    init {
        r = (c shr 16) and 0xFF
        g = (c shr 8) and 0xFF
        b = (c shr 0) and 0xFF
    }

    companion object{
        fun lerp(a: Colour, b: Colour, t: Float): Colour {
            return Colour((a.r + (b.r - a.r) * t).toInt(), (a.g + (b.g - a.g) * t).toInt(), (a.b + (b.b - a.b) * t).toInt())
        }
    }
}