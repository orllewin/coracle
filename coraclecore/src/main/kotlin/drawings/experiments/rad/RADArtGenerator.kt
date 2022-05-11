package drawings.experiments.rad

import coracle.CoracleEventListener
import coracle.Drawing
import kotlin.math.floor
import kotlin.math.min

class RADArtGenerator: Drawing() {

    val w = 512
    val h = 512

    val d = min(w, h)/16
    val m = Array(16 * 16) { -1 }

    override fun setup() {
        size(640, 512)
        interactiveMode(object: CoracleEventListener{
            override fun mousePressed() {
                when {
                    mouseX < 512 -> toggleCell()
                    else -> controlClick()
                }
            }

            override fun mouseReleased() = Unit//NOOP
        })
    }

    fun toggleCell(){
        val x = floor(mouseX/d.toDouble()).toInt()
        val y = floor(mouseY/d.toDouble()).toInt()
        m[y * 16 + x] = when (m[y * 16 + x]) {
            -1 -> 1
            else -> -1
        }
    }

    fun controlClick(){
        if(mouseY in 21..69){
            repeat(512){ index ->
                m[index] = -1
            }
        }

        if(mouseY in 91..139){
            val rawCells = m.joinToString { cv ->
                when (cv) {
                    -1 -> "X"
                    else -> "W"
                }
            }.replace(", ", "")

            val chars = rawCells.toCharArray()
            val units = mutableListOf<String>()

            var isOn = chars.first() == 'W'
            var count = 1
            chars.forEachIndexed { index, c ->
                if(index > 0){
                    if(c == 'W'){
                        //On
                        if(isOn){
                            count++
                        }else{
                            //Was off - but now we're on
                            if(count == 1){
                                units.add("0")
                            }else{
                                units.add("0.$count")
                            }
                            count = 1
                            isOn = true
                        }
                    }else{
                        //Off
                        if(!isOn){
                            count++
                        }else{
                            //Was on - but now we're off
                            if(count == 1){
                                units.add("1")
                            }else{
                                units.add("1.$count")
                            }
                            count = 1
                            isOn = false
                        }
                    }

                    if(index == 255){
                        if(isOn){
                            if(count == 1){
                                units.add("1")
                            }else{
                                units.add("1.$count")
                            }
                        }else{
                            if(count == 1){
                                units.add("0")
                            }else{
                                units.add("0.$count")
                            }
                        }
                    }
                }
            }
            val builder = StringBuilder()

            units.forEachIndexed { index, s ->
                when {
                    s.contains(".") -> builder.append("|$s|")
                    else -> builder.append(s)
                }
            }

            var output = builder.toString().replace("||", "|")

            if(output.endsWith("|")){
                //Surely this should be 1 eh Kotlin?
                output = output.dropLast(0)
            }
            if(output.startsWith("|")){
                output = output.substring(1, output.length - 1)
            }

            print("16x16:#0#f:$output")
        }
    }

    override fun draw() {
        background()

        //Draw grid
        fill(BLACK)
        noStroke()
        rect(0, 0, 512, 512)
        stroke(WHITE, 0.4f)

        //Vertical lines
        repeat(17){ x ->
            line(x * d, 0, x * d, height)
        }

        //Horizontal lines
        repeat(16){ y ->
            line(0, y * d, 512, y * d)
        }

        //Draw cells
        noStroke()
        fill(WHITE)
        translate(d/2, d/2)
        m.forEachIndexed { index, value ->
            val x = (index % 16) * d
            val y = ((index / 16) % 16) * d

            if(value != -1) circle(x, y, d/2)
        }
        translate(0, 0)

        //Draw controls
        //Clear
        fill(BLACK)
        noStroke()
        rect(532, 20, width -532 - 20, 50)
        fill(WHITE)
        text("CLEAR", 545, 52, 18)

        //Export
        fill(BLACK)
        noStroke()
        rect(532, 90, width -532 - 20, 50)
        fill(WHITE)
        text("EXPORT", 538, 122, 18)

        //RAD text
        fill(BLACK)
        text("RAD", 555, height - 30, 20)
    }
}