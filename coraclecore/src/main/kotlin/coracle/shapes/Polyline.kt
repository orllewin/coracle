package coracle.shapes

import coracle.Easel

class Polyline {

    private val lines = arrayListOf<Line>()

    var pointA: Point? = null
    var pointB: Point? = null

    fun addVertex(point: Point){
        when (pointA) {
            null -> pointA = point
            else -> pointB = point
        }
        update()
    }

    fun addVertex(x: Float, y: Float){
        when (pointA) {
            null -> pointA = Point(x, y)
            else -> pointB = Point(x, y)
        }
        update()
    }

    private fun update(){
        if(pointA != null && pointB != null){
            if(lines.isNotEmpty()) lines.add(Line(Point(lines.last().x2, lines.last().y2) , pointA!!))
            lines.add(Line(pointA!!, pointB!!))
            pointA = null
            pointB = null
        }
    }

    fun draw(){
        lines.forEach { line ->
            Easel.drawing?.line(line)
        }
    }
}