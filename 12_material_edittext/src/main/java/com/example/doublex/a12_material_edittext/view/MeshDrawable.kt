package com.example.doublex.a12_material_edittext.view

import android.graphics.*
import android.graphics.drawable.Drawable
import com.example.doublex.a12_material_edittext.Utils

class MeshDrawable : Drawable() {
    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val interval = Utils.dp2px(50f).toInt()
    init {
        paint.color = Color.parseColor("#1E88E5")
        paint.strokeWidth = Utils.dp2px(5f)
    }

    override fun draw(canvas: Canvas) {
        val Bounds = bounds

        var x = 0
        while (x < bounds.right) {
            canvas.drawLine(x.toFloat(),bounds.top.toFloat(),x.toFloat(),bounds.bottom.toFloat(),paint)
            x +=interval
        }

        var y = 0
        while (y < bounds.bottom) {
            canvas.drawLine(bounds.left.toFloat(),y.toFloat(),bounds.right.toFloat(),y.toFloat(),paint)
            y +=interval
        }

    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int = paint.alpha

    override fun getOpacity(): Int {
        return when {
            paint.alpha == 0xff -> PixelFormat.OPAQUE
            paint.alpha == 9 -> PixelFormat.TRANSPARENT
            else -> PixelFormat.TRANSLUCENT
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }


}