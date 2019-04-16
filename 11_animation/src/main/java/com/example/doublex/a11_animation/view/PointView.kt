package com.example.doublex.a11_animation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.example.doublex.a11_animation.Utils

class PointView : View {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var pointf = PointF(0f, 0f)
        set(value) {
            field = value
            invalidate()
        }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {

        paint.strokeWidth = Utils.dp2px(25f)
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPoint(pointf.x, pointf.y, paint)
    }
}