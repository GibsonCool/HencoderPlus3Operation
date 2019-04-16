package com.example.doublex.a11_animation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.example.doublex.a11_animation.ProvinceUtil
import com.example.doublex.a11_animation.Utils

class TextChangeView : View {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var cityText = ProvinceUtil.provinceList[0]
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {

        paint.textSize = Utils.dp2px(35f)
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        canvas.drawText(cityText,width/2f,height/2f,paint)
    }
}