
import coracle.Colour
import coracle.Drawing
import coracle.Drawing.Companion.TAU
import coracle.Renderer
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.floor
import kotlin.math.round


class WebRenderer(canvasId: String): Renderer() {

    private var canvas: HTMLCanvasElement = document.getElementById(canvasId) as HTMLCanvasElement
    private var context: CanvasRenderingContext2D = canvas.getContext("2d") as CanvasRenderingContext2D
    private var drawing: Drawing? = null

    private var looping = true

    override fun drawing(drawing: Drawing) {
        this.drawing = drawing
    }

    override fun start() {
        window.requestAnimationFrame { timestamp ->
            drawing?.draw()
            loop(timestamp)
        }
    }

    override fun noLoop() {
        looping = false
    }

    private fun loop(timestamp: Double){
        window.requestAnimationFrame { timestamp ->
            if(!looping) return@requestAnimationFrame
            drawing?.draw()
            loop(timestamp)
        }
    }

    override fun init(){

    }

    override fun size(width: Int, height: Int) {
        canvas.width = width
        canvas.height = height
    }

    override fun matchWidth() {
        if(canvas.width != window.innerWidth) {
            canvas.width = window.innerWidth
            drawing?.width = canvas.width
        }
    }

    override fun matchHeight() {
        if(canvas.height != window.innerHeight) {
            canvas.height = window.innerHeight
            drawing?.height = canvas.height
        }
    }

    override fun background(colour: Int) {
        context.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
        context.fillStyle = colourConvert(colour, 1f)
        context.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    }

    override fun smooth() { context.imageSmoothingEnabled = true }
    override fun noSmooth() { context.imageSmoothingEnabled = false }

    override fun line(x1: Int, y1: Int, x2: Int, y2: Int) {
        context.beginPath()
        context.moveTo(x1.toDouble(), y1.toDouble())
        context.lineTo(x2.toDouble(), y2.toDouble())
        context.strokeStyle = colourConvert(stroke, strokeAlpha)
        context.stroke()
        context.closePath()
    }

    override fun circle(cX: Int, cY: Int, r: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> {
                context.beginPath()
                context.arc(cX.toDouble(), cY.toDouble(), r.toDouble(), 0.0, TAU, false)
                context.strokeStyle = colourConvert(stroke, strokeAlpha)
                context.stroke()
                context.closePath()
            }
            DrawingMode.Fill -> {
                context.beginPath()
                context.arc(cX.toDouble(), cY.toDouble(), r.toDouble(), 0.0, TAU, false)
                context.fillStyle = colourConvert(fill, fillAlpha)
                context.fill()
                context.closePath()
            }
            DrawingMode.FillAndStroke -> {
                context.beginPath()
                context.arc(cX.toDouble(), cY.toDouble(), r.toDouble(), 0.0, TAU, false)
                context.fillStyle = colourConvert(fill, fillAlpha)
                context.fill()
                context.strokeStyle = colourConvert(stroke, strokeAlpha)
                context.stroke()
                context.closePath()
            }
        }
    }

    override fun point(x: Int, y: Int) {
        context.beginPath()
        context.fillRect(x.toDouble(), y.toDouble(), 1.0, 1.0)
        context.fillStyle = colourConvert(stroke, strokeAlpha)
        context.closePath()
    }

    override fun rect(x: Int, y: Int, width: Int, height: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> {
                context.beginPath()
                context.rect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
                context.strokeStyle = colourConvert(stroke, strokeAlpha)
                context.stroke()
                context.closePath()
            }
            DrawingMode.Fill -> {
                context.beginPath()
                context.fillStyle = colourConvert(fill, fillAlpha)
                context.fillRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
                context.closePath()
            }
            DrawingMode.FillAndStroke -> {
                context.beginPath()
                context.rect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
                context.fillStyle = colourConvert(fill, fillAlpha)
                context.fill()
                context.strokeStyle = colourConvert(stroke, strokeAlpha)
                context.stroke()
                context.closePath()
            }
        }
    }

    override fun text(text: String, x: Int, y: Int, size: Int) {
        context.fillStyle = colourConvert(fill, fillAlpha)
        context.fill()
        context.font = "${size}px sans-serif"
        context.fillText(text, x.toDouble(), y.toDouble())
    }

    private fun colourConvert(colour: Int, alpha: Float): String {
        val c = Colour(colour)
        return "rgba(${c.r}, ${c.g}, ${c.b}, ${alpha})"
    }
}