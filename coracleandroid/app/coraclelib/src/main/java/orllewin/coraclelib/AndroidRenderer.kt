package orllewin.coraclelib

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.Choreographer
import coracle.CoracleEventListener
import coracle.Drawing
import coracle.Renderer
import kotlin.math.floor

class AndroidRenderer(private val coracleView: CoracleView, private val printer: Printer? = null): Renderer() {

    interface Printer{
        fun print(message: String)
    }
    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = false
    }
    private val fillPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        isAntiAlias = false
    }
    private val strokePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        isAntiAlias = false
    }

    private var drawing: Drawing? = null
    private var w: Int = 0
    private var h: Int = 0

    private var canvas: Canvas? = null

    private fun l(message: String) = println("CORACLE: $message")

    init {
        platform = Platform.Android
        coracleView.onCanvas{ canvas ->
            this.canvas = canvas
            coracleView.invalidate()
        }

        coracleView.onDraww{ canvas ->
            this.canvas = canvas
            drawing?.draw()
            Choreographer.getInstance().postFrameCallback(choreographerCallback)
        }
        coracleView.onSizeChanged { width, height ->
            w = width
            h = height
            drawing?.width = width
            drawing?.height = height
        }
    }

    override fun interactiveMode(listener: CoracleEventListener?) {
        l("interactiveMode() not implemented")
    }

    override fun drawing(drawing: Drawing) { this.drawing = drawing }

    private val choreographerCallback = Choreographer.FrameCallback { coracleView.invalidate() }

    override fun start() {
        l("start")
        Choreographer.getInstance().postFrameCallback(choreographerCallback)
    }

    override fun noLoop() {
        l("noLoop() not implemented")
    }

    override fun init() {
        l("init() not implemented")
    }

    override fun print(out: String) { printer?.print(out) }

    override fun size(width: Int, height: Int){
        l("size(w, h) ignored on Android")
    }
    override fun matchWidth() = Unit//NOOP on Android
    override fun matchHeight() = Unit//NOOP on Android


    override fun background(colour: Int) {
        backgroundPaint.color = convert(colour)
        canvas?.drawRect(0F, 0F, w.toFloat(), h.toFloat(), backgroundPaint)
    }

    override fun smooth() {
        strokePaint.isAntiAlias = true
        fillPaint.isAntiAlias = true
    }

    override fun noSmooth() {
        strokePaint.isAntiAlias = false
        fillPaint.isAntiAlias = false
    }

    override fun line(x1: Int, y1: Int, x2: Int, y2: Int) {
        canvas?.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), strokePaint)
    }

    override fun circle(cX: Int, cY: Int, r: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> canvas?.drawCircle(cX.toFloat(), cY.toFloat(), r.toFloat(), strokePaint)
            DrawingMode.Fill -> canvas?.drawCircle(cX.toFloat(), cY.toFloat(), r.toFloat(), fillPaint)
            DrawingMode.FillAndStroke -> {
                canvas?.drawCircle(cX.toFloat(), cY.toFloat(), r.toFloat(), fillPaint)
                canvas?.drawCircle(cX.toFloat(), cY.toFloat(), r.toFloat(), strokePaint)
            }
        }
    }

    override fun point(x: Int, y: Int) {
        canvas?.drawPoint(x.toFloat(), y.toFloat(), strokePaint)
    }

    override fun rect(x: Int, y: Int, width: Int, height: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> canvas?.drawRect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), strokePaint)
            DrawingMode.Fill -> canvas?.drawRect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), fillPaint)
            DrawingMode.FillAndStroke -> {
                canvas?.drawRect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), fillPaint)
                canvas?.drawRect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat(), strokePaint)
            }
        }
    }

    override fun text(text: String, x: Int, y: Int, size: Int) = Unit//todo: Not implemented on Android
    override fun mouseX(): Int = -1//todo: Not implemented on Android
    override fun mouseY(): Int = -1//todo: Not implemented on Android

    override fun strokeWeight(weight: Int) { strokePaint.strokeWidth = weight.toFloat() }

    override fun stroke(colour: Int) {
        super.stroke(colour)
        strokePaint.color = convert(colour)
    }

    override fun stroke(colour: Int, alpha: Float) {
        super.stroke(colour, alpha)
        strokePaint.color = convert(colour)
        strokePaint.alpha = floor(alpha*255).toInt()
    }

    override fun fill(colour: Int){
        super.fill(colour)
        fillPaint.color = convert(colour)
    }

    override fun fill(colour: Int, alpha: Float) {
        super.fill(colour, alpha)
        fillPaint.color = convert(colour)
        fillPaint.alpha = floor(alpha*255).toInt()
    }

    private fun convert(colour: Int): Int = (colour.toLong() or -0x1000000).toInt()
}