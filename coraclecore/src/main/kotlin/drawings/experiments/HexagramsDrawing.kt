package drawings.experiments

import coracle.*
import kotlin.math.cos
import kotlin.math.sin

class HexagramsDrawing: Drawing() {

    private val hexagrams = Array(36) { Hexagram() }
    private val hexCoords = Array(6) { Coord(-1, -1) }

    override fun setup() {
        size(400, 400)
        translate(33, 33)

        val rads = TWO_PI/6
        hexCoords.forEachIndexed { index, coord ->
            coord.x = sin(rads * index).toFloat()
            coord.y = cos(rads * index).toFloat()
        }

        hexagrams.forEachIndexed { index, hexagram ->
            val xIndex = index % 6
            val yIndex = (index / 6) % 6
            hexagram.x = xIndex * 66
            hexagram.y = yIndex * 66
        }
    }

    override fun draw() {
        background(0x1d1d1d)
        hexagrams.forEach { hexagram ->
            hexagram.cast().draw()
        }
    }

    inner class Hexagram{
        var x = -1
        var y = -1
        var pointRefs = mutableListOf<Pair<Int, Int>>()
        var drawCircle = false

        fun cast(): Hexagram {
            if(pointRefs.isEmpty() || random(100) < 3) {
                pointRefs.clear()
                repeat(random(2, 20).toInt()) {
                    val i1 = random(0f, 6f).toInt()
                    val i2 = random(0f, 6f).toInt()
                    pointRefs.add(Pair(i1, i2))
                }
                drawCircle = random(100) < 5
            }
            return this
        }

        fun draw(){
            stroke(0xefefef, 0.5f)
            noFill()
            pointRefs.forEach { ref ->
                val c1 = hexCoords[ref.first]
                val c2 = hexCoords[ref.second]
                line(x + (c1.x * 20), y + (c1.y * 20), x + (c2.x * 20), y + (c2.y * 20))
            }
            if(drawCircle) circle(x, y, 18)
        }
    }
}