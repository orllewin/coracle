package coracle

import kotlin.math.round

fun Float.svg(): String{
    return "${round(this * 100) / 100}"
}