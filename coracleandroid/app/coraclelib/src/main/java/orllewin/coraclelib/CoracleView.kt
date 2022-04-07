package orllewin.coraclelib

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CoracleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attrs, defStyleAttr){

    private val tag = "CoracleView"

    private var initialised = false
    private var hardwareAccCheckRequired = true

    private var onSizeChanged: (width: Int, height: Int) -> Unit? = {_,_->}
    private var onDraw: (canvas: Canvas?) -> Unit? = { _->}
    private var onCanvas: (canvas: Canvas?) -> Unit? = { _->}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        onSizeChanged.invoke(w, h)
    }

    fun onCanvas(onCanvas: (canvas: Canvas?) -> Unit){
        this.onCanvas = onCanvas
    }
    fun onDraww(onDraw: (canvas: Canvas?) -> Unit){
        this.onDraw = onDraw
    }
    fun onSizeChanged(onSizeChanged: (width: Int, height: Int) -> Unit){
        this.onSizeChanged = onSizeChanged
    }

    override fun onDraw(canvas: Canvas?) {
        if(hardwareAccCheckRequired) hardwareAccelerationCheck(canvas)
        when {
            initialised -> onDraw.invoke(canvas)
            else -> {
                onCanvas(canvas)
                initialised = true
            }
        }
    }

    private fun hardwareAccelerationCheck(canvas: Canvas?){
        hardwareAccCheckRequired = false
        if(canvas == null) return

        when {
            canvas.isHardwareAccelerated -> Log.d(tag, "View is using hardware acceleration")
            else -> {
                Log.e(tag,"View is NOT using hardware acceleration")
                Log.e(tag,"See: https://developer.android.com/guide/topics/graphics/hardware-accel.html")
            }
        }
    }
}