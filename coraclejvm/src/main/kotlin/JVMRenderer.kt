
import coracle.Drawing
import coracle.Renderer
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JComponent
import javax.swing.JFrame
import kotlin.math.round

class JVMRenderer: Renderer() {

    private var timer = Timer("LluniauLoopTimer")
    private val frame = JFrame("Lluniau")

    private var w: Int = 0
    private var h: Int = 0

    private var g2d: Graphics2D? = null
    private var buffer:  BufferedImage? = null
    private var drawing: Drawing? = null

    private val component = object: JComponent() {
        override fun paint(g: Graphics?) {
            super.paint(g)

            if(buffer == null && w != 0){
                buffer = createImage(w, h) as BufferedImage
                g2d = buffer?.createGraphics()
                g2d?.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
                g2d?.color = Color(stroke)
            }

            buffer?.let{
                g?.drawImage(buffer, 0, 0, this)
            }
        }
    }

    override fun drawing(drawing: Drawing) {
        this.drawing = drawing
    }

    override fun start() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                drawing?.draw()
                component.repaint()
            }
        }, 18, 18)
    }

    override fun noLoop(){
        //todo
    }

    override fun init() {

    }

    override fun size(width: Int, height: Int) {
        this.w = width
        this.h = height
        frame.add(component)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isUndecorated = true
        frame.pack()
        frame.setSize(width, height)
        frame.setLocationRelativeTo(null)
        frame.isResizable = false
        frame.isVisible = true
    }



    override fun matchWidth() = Unit//todo
    override fun matchHeight() = Unit//todo

    override fun background(colour: Int) {
        g2d?.color = Color(colour)
        g2d?.fillRect(0, 0, drawing!!.width, drawing!!.height)
        stroke(stroke)
    }

    override fun smooth() { g2d?.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON) }
    override fun noSmooth() { g2d?.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF) }

    override fun line(x1: Int, y1: Int, x2: Int, y2: Int) {
        g2d?.color = strokeColor()
        g2d?.drawLine(x1, y1, x2, y2)
    }

    override fun circle(cX: Int, cY: Int, r: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> {
                g2d?.color = strokeColor()
                g2d?.drawOval(cX - r, cY - r, r * 2, r * 2)
            }
            DrawingMode.Fill -> {
                g2d?.color = fillColor()
                g2d?.fillOval(cX - r, cY - r, r * 2, r * 2)
            }
            DrawingMode.FillAndStroke -> {
                g2d?.color = fillColor()
                g2d?.fillOval(cX - r, cY - r, r * 2, r * 2)
                g2d?.color = strokeColor()
                g2d?.drawOval(cX - r, cY - r, r * 2, r * 2)
            }
        }
    }

    override fun point(x: Int, y: Int) {
        g2d?.color = strokeColor()
        g2d?.drawRect(x, y, 1, 1)
    }

    override fun rect(x1: Int, y1: Int, width: Int, height: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> {
                g2d?.color = strokeColor()
                g2d?.drawRect(x1, y1, width, height)
            }
            DrawingMode.Fill -> {
                g2d?.color = fillColor()
                g2d?.fillRect(x1, y1, width, height)
            }
            DrawingMode.FillAndStroke -> {
                g2d?.color = fillColor()
                g2d?.fillRect(x1, y1, width, height)
                g2d?.color = strokeColor()
                g2d?.drawRect(x1, y1, width, height)
            }
        }
    }

    override fun text(text: String, x: Int, y: Int, size: Int) = Unit//todo

    private fun strokeColor(): Color{
        return when (strokeAlpha) {
            1f -> Color(stroke)
            else -> {
                val c =  Color(stroke)
                Color(c.red, c.green, c.blue, round(strokeAlpha * 255.0).toInt())
            }
        }
    }

    private fun fillColor(): Color{
        return when (fillAlpha) {
            1f -> Color(fill)
            else -> {
                val c =  Color(fill)
                Color(c.red, c.green, c.blue, round(fillAlpha * 255.0).toInt())
            }
        }
    }
}