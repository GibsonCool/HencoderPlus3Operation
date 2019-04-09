package com.example.doublex.hencoderplus3operation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.doublex.hencoderplus3operation.Utils

class TestView : View {
    val RADIUS = Utils.dp2px(100f)

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    /**
     * 图形测量会经过多次，
     * 当测量结果和上次结果一样的时候没有必要再次做一些不一样的计算
     * 只有当结果有变化的时候回走这个方法
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        path.addRect(width / 2f - RADIUS, height / 2f, width / 2f + RADIUS, height / 2 + RADIUS, Path.Direction.CCW)
        path.fillType = Path.FillType.EVEN_ODD
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        canvas.drawLine(100f,100f,400f,400f,paint)
//        canvas.drawCircle(width/2f,height/2f,Utils.dp2px(150f),paint)

        canvas.drawPath(path, paint)

    }
}