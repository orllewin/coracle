package orllewin.coracleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import coracle.Drawing
import drawings.experiments.PseudoSpheres
import drawings.experiments.PseudoTorus
import drawings.experiments.boids.SelfOrganising2
import drawings.experiments.boids.tadpoles.TadpolesTailsDark
import drawings.experiments.three_d.Lines3D
import drawings.experiments.three_d.SphereMesh
import drawings.experiments.three_d.TriangleStripSphere
import orllewin.coraclelib.AndroidRenderer
import orllewin.coraclelib.CoracleView

class MainActivity: AppCompatActivity() {

    val drawings = mutableListOf<Drawing>()
    lateinit var coracleView: CoracleView
    lateinit var renderer: AndroidRenderer
    var drawingIndex = 0
    var activeDrawing: Drawing

    init {
        drawings.add(PseudoSpheres())
        drawings.add(Lines3D())
        drawings.add(TriangleStripSphere())
        drawings.add(SelfOrganising2())
        drawings.add(PseudoTorus())
        drawings.add(SphereMesh())
        drawings.add(TadpolesTailsDark())

        activeDrawing = drawings.first()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coracleView = findViewById(R.id.coracle_view)
        renderer = AndroidRenderer(coracleView)

        val nextButton = findViewById<AppCompatImageButton>(R.id.next_button)
        nextButton.setOnClickListener {
            drawingIndex++
            if(drawingIndex <= drawings.size - 1){
                activeDrawing.clear()
                activeDrawing = drawings[drawingIndex]
                startDrawing()
            }else{
                drawingIndex--
            }
        }
        val prevButton = findViewById<AppCompatImageButton>(R.id.prev_button)
        prevButton.setOnClickListener {
            drawingIndex--
            if(drawingIndex >= 0){
                activeDrawing.clear()
                activeDrawing = drawings[drawingIndex]
                startDrawing()
            }else{
                drawingIndex = 0
            }
        }

        startDrawing()
    }

    fun startDrawing() {
        activeDrawing.width = coracleView.width
        activeDrawing.height = coracleView.height
        activeDrawing.renderer(renderer).start()
        coracleView.reset()
    }
}