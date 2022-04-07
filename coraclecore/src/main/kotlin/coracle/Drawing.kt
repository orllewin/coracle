package coracle

import coracle.shapes.Circle
import coracle.shapes.Line
import coracle.shapes.Rect

abstract class Drawing {

    var width = 100
    var height = 100

    var xTranslation: Int = 0
    var yTranslation: Int = 0
    val matrixStack = mutableListOf<Pair<Int, Int>>()

    private val onetimeKeys = arrayListOf<Int>()
    private val onetimeNoForegroundAndroid = 1

    fun pushMatrix(){
        matrixStack.add(Pair(xTranslation, yTranslation))
    }

    fun popMatrix(){
        val translation = matrixStack.removeLast()
        xTranslation = translation.first
        yTranslation = translation.second
    }

    companion object{
        const val BLACK = 0x000000
        const val WHITE = 0xffffff

        const val GREEN_BACKGROUND = 0xecf8f0
        const val RED_BACKGROUND = 0xf8ecf0
        const val BLUE_BACKGROUND = 0xecf0f8

        const val ORANGE_FOREGROUND = 0xDD9920
        const val BLUE_FOREGROUND = 0x205F77

        const val DEFAULT_BACKGROUND = GREEN_BACKGROUND
        const val DEFAULT_FOREGROUND = BLUE_FOREGROUND
    }

    private lateinit var renderer: Renderer

    fun renderer(renderer: Renderer): Drawing {
        this.renderer = renderer
        renderer.init()
        return this
    }

    abstract fun setup()
    abstract fun draw()

    fun start() {
        Easel.drawing = this
        setup()
        renderer.drawing(this)
        renderer.start()
    }

    fun print(output: String) = renderer.print(output)

    fun onetimePrint(key: Int, message: String){
        when {
            !onetimeKeys.contains(key) -> {
                onetimeKeys.add(key)
                print(message)
            }
        }
    }

    fun noLoop() = renderer.noLoop()

    fun size(width: Int, height: Int){
        when (renderer.platform) {
            Renderer.Platform.Android -> print("size(w,h) ignored on Android")
            else -> {
                this.width = width
                this.height = height
                renderer.size(width, height)
            }
        }
    }

    fun translate(x: Int, y: Int){
        xTranslation = x
        yTranslation = y
    }

    fun translate(x: Float, y: Float) = translate(x.toInt(), y.toInt())

    fun smooth() = renderer.smooth()
    fun noSmooth() = renderer.noSmooth()

    fun matchWidth() = renderer.matchWidth()
    fun matchHeight() = renderer.matchHeight()
    fun matchWindow() {
        renderer.matchWidth()
        renderer.matchHeight()
    }

    fun background(colour: Int) = renderer.background(colour)
    fun background(colour: Colour) = renderer.background(colour.c)
    fun background() = renderer.background(WHITE)
    fun foreground(colour: Int, alpha: Float){
        when {
            isAndroid() -> onetimePrint(onetimeNoForegroundAndroid, "foreground(c,a) not supported on Android")
            else -> {
                val mode = renderer.drawingMode
                noStroke()
                pushMatrix()
                translate(0, 0)
                fill(colour, alpha)
                rect(0, 0, width, height)
                popMatrix()
                renderer.drawingMode = mode
            }
        }
    }
    fun fill(colour: Int) = renderer.fill(colour)
    fun fill(colour: Colour) = renderer.fill(colour.c)
    fun fill(colour: Int, alpha: Float) = renderer.fill(colour, alpha)
    fun fill(colour: Colour, alpha: Float) = renderer.fill(colour.c, alpha)
    fun noFill() = renderer.noFill()

    fun strokeWeight(weight: Int) = renderer.strokeWeight(weight)
    fun stroke(colour: Int) = renderer.stroke(colour)
    fun stroke(colour: Colour) = renderer.stroke(colour.c)
    fun stroke(colour: Colour, alpha: Float) = renderer.stroke(colour.c, alpha)
    fun stroke(colour: Int, alpha: Float) = renderer.stroke(colour, alpha)
    fun stroke(colour: Int, alpha: Double) = renderer.stroke(colour, alpha.toFloat())
    fun noStroke() = renderer.noStroke()

    //Shape primitives
    fun line(x1: Int, y1: Int, x2: Int, y2: Int) = renderer.line(x1 + xTranslation, y1 + yTranslation, x2 + xTranslation, y2 + yTranslation)
    fun line(x1: Float, y1: Float, x2: Float, y2: Float) = renderer.line(x1.toInt() +xTranslation, y1.toInt() + yTranslation, x2.toInt() + xTranslation, y2.toInt() + yTranslation)
    fun line(line: Line) = renderer.line(line.x1.toInt() +xTranslation, line.y1.toInt() + yTranslation, line.x2.toInt() + xTranslation, line.y2.toInt() + yTranslation)

    fun circle(circle: Circle) = renderer.circle(circle.x.toInt() + xTranslation, circle.y.toInt() + yTranslation, circle.r.toInt())
    fun circle(cX: Number, cY: Number, r: Number) = renderer.circle(cX.toInt() + xTranslation, cY.toInt() + yTranslation, r.toInt())
    fun circle(cX: Int, cY: Int, r: Int) = renderer.circle(cX + xTranslation, cY + yTranslation, r)
    fun circle(cX: Float, cY: Float, r: Int) = renderer.circle(cX.toInt() + xTranslation, cY.toInt() + yTranslation, r)

    fun point(x: Int, y: Int) = renderer.point(x + xTranslation, y + yTranslation)
    fun point(x: Float, y: Float) = renderer.point(x.toInt() + xTranslation, y.toInt() + yTranslation)

    fun rect(x1: Int, y1: Int, width: Int, height: Int) = renderer.rect(x1 + xTranslation, y1 + yTranslation, width, height)
    fun rect(rect: Rect) = renderer.rect(rect.x.toInt(), rect.y.toInt(), rect.width.toInt(), rect.height.toInt())
    fun square(cX: Int, cY: Int, d: Int) = renderer.rect(cX - d/2 + xTranslation, cY - d/2 + yTranslation, d, d)

    fun text(text: String, x: Int, y: Int, size: Int) = renderer.text(text, x, y, size)

    fun interactiveMode(listener: CoracleEventListener? = null) = renderer.interactiveMode(listener)

    var mouseX: Int
        get() = renderer.mouseX()
        set(value) {}


    var mouseY: Int
        get() = renderer.mouseY()
        set(value) {}

    //Platform Specifics
    fun isAndroid(): Boolean = renderer.platform == Renderer.Platform.Android
    fun isWeb(): Boolean = renderer.platform == Renderer.Platform.Web
    fun isJVM(): Boolean = renderer.platform == Renderer.Platform.JVM
}