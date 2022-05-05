import com.soywiz.klock.Frequency
import com.soywiz.korge.view.*
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.vector.VectorPath
import coracle.*
import coracle.Image

class KorgeRenderer(private val stage: Stage) : Renderer() {

    private var drawing: Drawing? = null
    private var looping = true

    private var korgeTransparent = RGBA(0, 0, 0, 0)
    private var korgeFillColour = RGBA(0, 0, 255)
    private var korgeStrokeColour = RGBA(0, 0, 255)

    override fun print(out: String) = println(out)

    override fun interactiveMode(listener: CoracleEventListener?) = notImplemented("interactiveMode(listener: CoracleEventListener?)")

    override fun fill(colour: Int) {
        val c = Colour(colour)
        korgeFillColour = RGBA(c.r, c.g, c.b)
        super.fill(colour)
    }

    override fun stroke(colour: Int) {
        val c = Colour(colour)
        korgeStrokeColour = RGBA(c.r, c.g, c.b)
        super.stroke(colour)
    }

    override fun stroke(colour: Int, alpha: Float) {
        val c = Colour(colour)
        korgeStrokeColour = RGBA(c.r, c.g, c.b)
        //todo - do something with alpha
        super.stroke(colour)
    }

    override fun drawing(drawing: Drawing) {
        this.drawing = drawing
    }

    override fun start() {
        drawing?.frame = 0

        stage.scale = 2.0

        stage.addUpdater(Frequency(30.0)){
            drawing?.incFrame()
            drawing?.draw()

            if(!looping){
                //todo - how to clear updater?
            }
        }
    }

    override fun noLoop() {
        looping = false
    }

    override fun init() = Unit//NOOP

    override fun size(width: Int, height: Int) {
        stage.setSize(width.toDouble(), height.toDouble())
    }

    override fun matchWidth()= notAvailable("matchWidth()")
    override fun matchHeight() = notAvailable("matchHeight()")
    override fun clear() = notAvailable("clear()")

    override fun background(colour: Int) {
        stage.removeChildren()

        Colour(colour).run {
            val background = SolidRect(stage.width, stage.height, RGBA(r, g, b))
            stage.addChild(background)
        }
    }

    override fun smooth() = notAvailable("smooth()")
    override fun noSmooth() = notAvailable("noSmooth()")
    override fun background() = notAvailable("background()")

    override fun nativeColour(key: String): Int {
        notAvailable("nativeColour(key: String)")
        return -1
    }

    override fun line(x1: Int, y1: Int, x2: Int, y2: Int) {
        stage.addChild(Line(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble()).apply {
            this.tint = korgeStrokeColour
        })
    }

    override fun circle(cX: Int, cY: Int, r: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> {
                stage.addChild(Circle(r.toDouble(), korgeFillColour, korgeTransparent).xy(cX-r, cY-r))
            }
            DrawingMode.Fill -> {
                stage.addChild(Circle(r.toDouble(), korgeFillColour).xy(cX-r, cY-r))
            }
            DrawingMode.FillAndStroke -> {
                stage.addChild(Circle(r.toDouble(), korgeFillColour, korgeStrokeColour).xy(cX-r, cY-r))
            }
        }
    }

    override fun triangle(xA: Int, yA: Int, xB: Int, yB: Int, xC: Int, yC: Int){
        val triVectorPath = VectorPath()
        triVectorPath.moveTo(xA.toDouble(), yA.toDouble())
        triVectorPath.lineTo(xB.toDouble(), yB.toDouble())
        triVectorPath.lineTo(xC.toDouble(), yC.toDouble())
        triVectorPath.lineTo(xA.toDouble(), yA.toDouble())
        when (drawingMode) {
            DrawingMode.Stroke -> {
                stage.addChild(ShapeView(triVectorPath, korgeTransparent, korgeStrokeColour))
            }
            DrawingMode.Fill -> {
                stage.addChild(ShapeView(triVectorPath, korgeFillColour))
            }
            DrawingMode.FillAndStroke -> {
                stage.addChild(ShapeView(triVectorPath, korgeFillColour, korgeStrokeColour))
            }
        }

    }

    override fun point(x: Int, y: Int) {
        stage.addChild(SolidRect(1.0, 1.0, korgeStrokeColour).xy(x, y))
    }

    override fun rect(x: Int, y: Int, width: Int, height: Int) {
        when (drawingMode) {
            DrawingMode.Stroke -> {
                //todo - working?
                stage.addChild(StrokeRect(width, height, korgeStrokeColour).xy(x, y))
            }
            DrawingMode.Fill -> {
                stage.addChild(SolidRect(width, height, korgeFillColour).xy(x, y))
            }
            DrawingMode.FillAndStroke -> {
                //todo - working?
                stage.addChild(SolidRect(width, height, korgeFillColour).xy(x, y))
                stage.addChild(StrokeRect(width, height, korgeStrokeColour).xy(x, y))
            }
        }
    }

    override fun text(text: String, x: Int, y: Int, size: Int) = notImplemented("text(text: String, x: Int, y: Int, size: Int)")
    override fun loadImage(key: String, onReady: (image: Image?) -> Unit) = notImplemented("loadImage(key: String, onReady: (image: Image?) -> Unit)")

    override fun image(image: Image, x: Int, y: Int) = notImplemented("image(image: Image, x: Int, y: Int)")
    override fun image(image: Image, x: Int, y: Int, width: Int, height: Int) = notImplemented("image(image: Image, x: Int, y: Int, width: Int, height: Int)")

    override fun loadPixels() = notImplemented("loadPixels()")

    override fun pixel(x: Int, y: Int): Int? {
        notImplemented("pixel(x: Int, y: Int)")
        return -1
    }

    override fun mouseX(): Int {
        notImplemented("mouseX()")
        return -1
    }

    override fun mouseY(): Int {
        notImplemented("mouseY()")
        return -1
    }

    override fun strokeWeight(weight: Int) = notImplemented("strokeWeight(weight: Int)")

    private fun notAvailable(method: String) = print("KorgeRenderer does not support the $method method")
    private fun notImplemented(method: String) = print("KorgeRenderer $method not implemented yet")
}