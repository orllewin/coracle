package coracle.shapes

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
            lines.add(Line(pointA!!, pointB!!))
            pointA = null
            pointB = null
        }
    }
}