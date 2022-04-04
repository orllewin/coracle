package coracle

import coracle.shapes.Line
import coracle.shapes.Point

class SVG(width: Int, height: Int, backgroundColour: String = "#ffffff") {

    private val sb = StringBuilder()
    private val newline = "\n"

    init {
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
        sb.append("<svg width=\"$width\" height=\"$height\" viewBox=\"0 0 $width $height\" style=\"background-color:$backgroundColour\" xmlns=\"http://www.w3.org/2000/svg\">\n")
    }

    fun addLine(svg: String){
        sb.append("$svg$newline")
    }

    fun append(str: String){
        sb.append(str)
    }

    fun build(): String {
        sb.append("</svg>")
        return sb.toString()
    }

    companion object{
        fun svgLine(line: Line, colour: String, opacity: Float = 1.0f): String{
            return "<line x1=\"${line.x1.svg()}\" y1=\"${line.y1.svg()}\" x2=\"${line.x2.svg()}\" y2=\"${line.y2.svg()}\" style=\"stroke:$colour;stroke-width:1;opacity:$opacity;\" />"
        }

        fun svgPolyline(points: List<Point>, colour: String, opacity: Float = 1.0f): String{
            val sb = StringBuilder()
            sb.append("<polyline points=\"")
            for (i in points.indices) {
                sb.append("${points[i].x.svg()},${points[i].y.svg()} ")
            }
            sb.dropLast(1)
            sb.append("\" style=\"stroke:$colour;fill:none;opacity:$opacity;\" />")
            return sb.toString()
        }
    }
}