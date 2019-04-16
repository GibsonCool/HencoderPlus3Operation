package com.example.doublex.a11_animation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.doublex.a11_animation.Utils

class CircleView : View {
    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    //需要做属性改变的字段提供set/get方法，
    var radius = Utils.dp2px(50f)
        set(value) {
            field = value
            //并在set方法中标记为无效，会在下次刷新的时候从新绘制
            invalidate()
        }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }

}