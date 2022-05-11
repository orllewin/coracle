package drawings.experiments

import coracle.Drawing

class Matrix16x16: Drawing() {

    val w = 460
    val h = 400

    val diameter = 10
    val radius = (diameter/2)

    val xC = w/diameter
    val yC = h/diameter

    val m = Array(xC * yC) { -1 }


    override fun setup() {
        size(w, h)
        buildText("ABCDEFGHIJKLMNOPQRSTUVWXYZ\n12345678910\n\ncoracle @*")
        translate(radius * 4, radius * 4)
        noStroke()
    }

    override fun draw() {
        background(0x1d1d1d)

        m.forEachIndexed { index, value ->
            val x = (index % xC) * diameter
            val y = ((index / xC) % yC) * diameter

            if(value != -1){
                fill(value)
                circle(x, y, radius)
            }
        }

        noLoop()
    }

    fun buildText(text: String){
        var x = 0
        var y = 0
        text.lowercase().toCharArray().forEachIndexed { index, c ->
            val cm: Array<CharArray> = when (c) {
                '\n' -> cNewLine
                ' ' -> cSpace
                'a' -> ca
                'b' -> cb
                'c' -> cc
                'd' -> cd
                'e' -> ce
                'f' -> cf
                'g' -> cg
                'h' -> ch
                'i' -> ci
                'j' -> cj
                'k' -> ck
                'l' -> cl
                'm' -> cm
                'n' -> cn
                'o' -> co
                'p' -> cp
                'q' -> cq
                'r' -> cr
                's' -> cs
                't' -> ct
                'u' -> cu
                'v' -> cv
                'w' -> cw
                'x' -> cx
                'y' -> cy
                'z' -> cz
                '0' -> co
                '1' -> c1
                '2' -> c2
                '3' -> c3
                '4' -> c4
                '5' -> c5
                '6' -> c6
                '7' -> c7
                '8' -> c8
                '9' -> c9
                '.' -> cDot
                '@' -> cCC
                '*' -> cStar
                else -> cNull
            }
            var cy = y
            if(cm.contentEquals(cNewLine)){
                y += 6
                cy = y
                x = 0
            }else{
                cm.forEach { line ->
                    var cx = x
                    if(cx + line.size > xC - 3){
                        y += 6
                        cy = y
                        x = 0
                        cx = 0
                    }
                    line.forEach { c ->
                        if(c == 'X') m[cy * xC + cx] = 0xefefef
                        cx++
                    }
                    cy++
                }

                x += cm.first().size + 1
            }
        }
    }

    val cNewLine = arrayOf(charArrayOf('\n'))

    val cNull = arrayOf(
        charArrayOf(' '),
        charArrayOf(' '),
        charArrayOf('X'),
        charArrayOf(' '),
        charArrayOf(' '),
    )
    val cDot = arrayOf(
        charArrayOf(' '),
        charArrayOf(' '),
        charArrayOf(' '),
        charArrayOf(' '),
        charArrayOf('X'),
    )
    val cSpace = arrayOf(
        charArrayOf(' '),
        charArrayOf(' '),
        charArrayOf(' '),
        charArrayOf(' '),
        charArrayOf(' '),
    )
    val ca = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
    )
    val cb = arrayOf(
        charArrayOf('X', 'X', ' '),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )
    val cc = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
    )
    val cd = arrayOf(
        charArrayOf('X', 'X', ' '),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', ' '),
    )
    val ce = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
    )
    val cf = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', ' ', ' '),
    )
    val cg = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )
    val ch = arrayOf(
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
    )

    val ci = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
        charArrayOf('X', 'X', 'X'),
    )
    val cj = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', ' '),
    )
    val ck = arrayOf(
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', ' '),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
    )

    val cl = arrayOf(
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
    )
    val cm = arrayOf(
        charArrayOf('X', 'X', 'X', 'X', 'X'),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
    )
    val cn = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
    )

    val co = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )
    val cp = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', ' ', ' '),
    )
    val cq = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf(' ', ' ', 'X'),
    )
    val cr = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', ' '),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
    )
    val cs = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )
    val ct = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
    )
    val cu = arrayOf(
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )
    val cv = arrayOf(
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf(' ', 'X', ' '),
    )
    val cw = arrayOf(
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', 'X', 'X', 'X', 'X'),
    )
    val cx = arrayOf(
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf(' ', 'X', ' '),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
    )
    val cy = arrayOf(
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
    )
    val cz = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf(' ', 'X', ' '),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
    )

    val c1 = arrayOf(
        charArrayOf(' ', 'X'),
        charArrayOf('X', 'X'),
        charArrayOf(' ', 'X'),
        charArrayOf(' ', 'X'),
        charArrayOf(' ', 'X'),
    )

    val c2 = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf(' ', 'X', ' '),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
    )

    val c3 = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf(' ', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )

    val c4 = arrayOf(
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf(' ', ' ', 'X'),
    )

    val c5 = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )

    val c6 = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', ' '),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )

    val c7 = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
        charArrayOf(' ', 'X', ' '),
    )

    val c8 = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
    )

    val c9 = arrayOf(
        charArrayOf('X', 'X', 'X'),
        charArrayOf('X', ' ', 'X'),
        charArrayOf('X', 'X', 'X'),
        charArrayOf(' ', ' ', 'X'),
        charArrayOf(' ', ' ', 'X'),
    )
    val cCC = arrayOf(
        charArrayOf(' ', 'X', 'X', 'X', 'X', ' '),
        charArrayOf('X', ' ', ' ', ' ', ' ', 'X'),
        charArrayOf('X', 'X', ' ', 'X', ' ', 'X'),
        charArrayOf('X', ' ', ' ', ' ', ' ', 'X'),
        charArrayOf(' ', 'X', 'X', 'X', 'X', ' '),
    )

    val cStar= arrayOf(
        charArrayOf('X', ' ', 'X', ' ', 'X'),
        charArrayOf(' ', 'X', 'X', 'X', ' '),
        charArrayOf('X', 'X', 'X', 'X', 'X'),
        charArrayOf(' ', 'X', 'X', 'X', ' '),
        charArrayOf('X', ' ', 'X', ' ', 'X'),
    )
}