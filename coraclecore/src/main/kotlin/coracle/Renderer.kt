package coracle

abstract class Renderer {

    sealed class DrawingMode{
        object Stroke: DrawingMode()
        object Fill: DrawingMode()
        object FillAndStroke: DrawingMode()
    }

    var drawingMode: DrawingMode = DrawingMode.FillAndStroke
    var stroke: Int = 0x000000
    var strokeAlpha: Float = 1f
    var fill: Int = 0xffffff
    var fillAlpha = 1f

    abstract fun drawing(drawing: Drawing)
    abstract fun start()
    abstract fun noLoop()
    abstract fun init()
    abstract fun size(width: Int, height: Int)
    abstract fun matchWidth()
    abstract fun matchHeight()

    abstract fun background(colour: Int)

    abstract fun smooth()
    abstract fun noSmooth()

    //Primitives
    abstract fun line(x1: Int, y1: Int, x2: Int, y2: Int)
    abstract fun circle(cX: Int, cY: Int, r: Int)
    abstract fun point(x: Int, y: Int)
    abstract fun rect(x: Int, y: Int, width: Int, height: Int)
    abstract fun text(text: String, x: Int, y: Int, size: Int)

    open fun stroke(colour: Int) {
        stroke = colour
        strokeAlpha = 1f
        if(drawingMode == DrawingMode.Fill) drawingMode = DrawingMode.FillAndStroke
    }
    open fun stroke(colour: Int, alpha: Float) {
        stroke = colour
        strokeAlpha = alpha
        if(drawingMode == DrawingMode.Fill) drawingMode = DrawingMode.FillAndStroke
    }
    open fun fill(colour: Int) {
        fill = colour
        fillAlpha = 1f
        if(drawingMode == DrawingMode.Stroke) drawingMode = DrawingMode.FillAndStroke
    }
    open fun fill(colour: Int, alpha: Float) {
        fill = colour
        fillAlpha = alpha
        if(drawingMode == DrawingMode.Stroke) drawingMode = DrawingMode.FillAndStroke
    }

    fun noStroke(){ drawingMode = DrawingMode.Fill }
    fun noFill(){ drawingMode = DrawingMode.Stroke }


}