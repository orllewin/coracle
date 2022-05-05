package coracle.shapes

import coracle.Easel
import coracle.draw

class Bezier(
    anchor1: Point,
    control1: Point,
    control2: Point,
    anchor2: Point,
    detail: Int = Easel.bezierDetail){

    constructor(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, x4: Float, y4: Float): this(Point(x1, y1), Point(x2, y2), Point(x3, y3), Point(x4, y4))

    val points = List(detail + 1) { Point(0f, 0f) }
    val lines = mutableListOf<Line>()

    init {
        for (i in 0..detail) {
            val t = i.toDouble() / detail
            val u = 1.0 - t
            val a = u * u * u
            val b = 3.0 * t * u * u
            val c = 3.0 * t * t * u
            val d = t * t * t
            points[i].x = (a * anchor1.x + b * control1.x + c * control2.x + d * anchor2.x).toFloat()
            points[i].y = (a * anchor1.y + b * control1.y + c * control2.y + d * anchor2.y).toFloat()
        }

        for (i in 0 until detail) {
            val j = i + 1
            lines.add(Line(points[i].x, points[i].y, points[j].x, points[j].y))
        }
    }

    fun draw() = lines.draw()
}