package coracle.shapes

class Bezier(
    p1: Point,
    p2: Point,
    p3: Point,
    p4: Point,
    val detail: Int){

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
            points[i].x = (a * p1.x + b * p2.x + c * p3.x + d * p4.x).toFloat()
            points[i].y = (a * p1.y + b * p2.y + c * p3.y + d * p4.y).toFloat()
        }

        for (i in 0 until detail) {
            val j = i + 1
            lines.add(Line(points[i].x, points[i].y, points[j].x, points[j].y))
        }
    }
}