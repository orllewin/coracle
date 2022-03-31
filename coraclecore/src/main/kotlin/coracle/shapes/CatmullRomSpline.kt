package coracle.shapes

import kotlin.math.pow

/**
 * References:
 * - https://stackoverflow.com/a/19283471
 * - https://github.com/Tractive/CatmullRomSpline-kotlin
 */
class CatmullRomSpline(
    private val type: Type,
    private val detail: Int) {
    private val vertices = arrayListOf<Point>()
    val points = arrayListOf<Point>()
    val lines = arrayListOf<Line>()

    init {
        if(detail < 2) throw IllegalArgumentException("Detail must be >= 2")
    }

    fun addVertex(point: Point){
        vertices.add(point)
    }
    fun addVertex(x: Float, y: Float){
        vertices.add(Point(x, y))
    }

    fun build() {
        if (vertices.size < 3) throw IllegalArgumentException("Not enough vertices")
        //Calculate start
        var dx: Double = vertices[1].x - vertices[0].x.toDouble()
        var dy: Double = vertices[1].y - vertices[0].y.toDouble()
        val x1: Double = vertices[0].x - dx
        val y1: Double = vertices[0].y - dy
        val start = Point(x1, y1)
        //Calculate end
        val n: Int = vertices.size - 1
        dx = vertices[n].x - vertices[n - 1].x.toDouble()
        dy = vertices[n].y - vertices[n - 1].y.toDouble()
        val xn: Double = vertices[n].x + dx
        val yn: Double = vertices[n].y + dy
        val end = Point(xn, yn)
        vertices.add(0, start)
        vertices.add(end)
        repeat(vertices.size - 3) { index ->
            val interpolatedPoints: List<Point> = interpolate(index)
            if (this.points.size > 0)  points.removeAt(0)
            points.addAll(interpolatedPoints)
        }

        var count = points.size
        if(count % 2 != 0) count--
        for (j in 1 until count) {
            val i = j - 1
            lines.add(Line(points[i].x, points[i].y, points[j].x, points[j].y))
        }
    }

    private fun interpolate(index: Int): List<Point>{
        val result = arrayListOf<Point>()
        val x = DoubleArray(4)
        val y = DoubleArray(4)
        val time = DoubleArray(4)
        for (i in 0..3) {
            x[i] = vertices[index + i].x.toDouble()
            y[i] = vertices[index + i].y.toDouble()
            time[i] = i.toDouble()
        }
        var tstart = 1.0
        var tend = 2.0
        if (type != Type.UNIFORM) {
            var total = 0.0
            for (i in 1..3) {
                val dx = x[i] - x[i - 1]
                val dy = y[i] - y[i - 1]
                total += when (type) {
                    Type.CENTRIPETAL -> (dx * dx + dy * dy).pow(.25)
                    else -> (dx * dx + dy * dy).pow(.5)
                }
                time[i] = total
            }
            tstart = time[1]
            tend = time[2]
        }
        val segments: Int = detail - 1
        result.add(vertices[index + 1])
        for (i in 1 until segments) {
            val xi: Double = interpolate(x, time, tstart + i * (tend - tstart) / segments)
            val yi: Double = interpolate(y, time, tstart + i * (tend - tstart) / segments)
            result.add(Point(xi, yi))
        }
        result.add(vertices[index + 2])
        return result
    }
    private fun interpolate(p: DoubleArray, time: DoubleArray, t: Double): Double {
        val L01 = p[0] * (time[1] - t) / (time[1] - time[0]) + p[1] * (t - time[0]) / (time[1] - time[0])
        val L12 = p[1] * (time[2] - t) / (time[2] - time[1]) + p[2] * (t - time[1]) / (time[2] - time[1])
        val L23 = p[2] * (time[3] - t) / (time[3] - time[2]) + p[3] * (t - time[2]) / (time[3] - time[2])
        val L012 = L01 * (time[2] - t) / (time[2] - time[0]) + L12 * (t - time[0]) / (time[2] - time[0])
        val L123 = L12 * (time[3] - t) / (time[3] - time[1]) + L23 * (t - time[1]) / (time[3] - time[1])
        return L012 * (time[2] - t) / (time[2] - time[1]) + L123 * (t - time[1]) / (time[2] - time[1])
    }
    enum class Type(val alpha: Double) {
        UNIFORM(0.0),
        CHORDAL(1.0),
        CENTRIPETAL(0.5)
    }
}