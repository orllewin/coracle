package coracle

import coracle.shapes.Line
import kotlin.math.round

fun Float.svg(): String{
    return "${round(this * 100) / 100}"
}

fun List<Line>.draw() = forEach(Line::draw)