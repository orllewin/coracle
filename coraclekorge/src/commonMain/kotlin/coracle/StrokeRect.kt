package coracle

import com.soywiz.korge.render.RenderContext
import com.soywiz.korge.render.useLineBatcher
import com.soywiz.korge.view.RectBase
import com.soywiz.korge.view.SolidRect
import com.soywiz.korim.bitmap.Bitmaps
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

class StrokeRect(width: Double, height: Double, color: RGBA = Colors.WHITE): RectBase() {

    companion object {
        operator fun invoke(width: Int, height: Int, color: RGBA = Colors.WHITE) = StrokeRect(width.toDouble(), height.toDouble(), color)
        operator fun invoke(width: Float, height: Float, color: RGBA = Colors.WHITE) = StrokeRect(width.toDouble(), height.toDouble(), color)
    }

    /** The [color] of this [SolidRect]. Alias of [colorMul]. */
    var color: RGBA
        set(value) { colorMul = value }
        get() = colorMul

    init {
        this.colorMul = color
    }

    override var width: Double = width; set(v) { field = v; dirtyVertices = true }
    override var height: Double = height; set(v) { field = v; dirtyVertices = true }

    override val bwidth: Double get() = width
    override val bheight: Double get() = height

    override fun renderInternal(ctx: RenderContext) {
        if (!visible) return
        if (baseBitmap === Bitmaps.transparent) return
        if (dirtyVertices) {
            dirtyVertices = false
            computeVertices()
        }

        ctx.useLineBatcher { batcher ->
            batcher.line(x, y, x + width, y)
            batcher.line(x  + width, y, x + width, y + height)
            batcher.line(x + width, y + height, x, y + height)
            batcher.line(x, y + height, x, y)
        }
    }
}