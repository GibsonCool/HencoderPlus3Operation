package com.example.doublex.hencoderplus3operation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.doublex.hencoderplus3operation.Utils

class PieChart : View {

    private val RADIUS = Utils.dp2px(150f)

    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val bounds = RectF()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val colors: Array<Int> = arrayOf(
        Color.parseColor("#448AFF"),
        Color.parseColor("#D81B60"),
        Color.parseColor("#43A047"),
        Color.parseColor("#FDD835")
    )

    private val angles: Array<Float> = arrayOf(60f, 100f, 120f, 80f)

    private val pullIndex = 1
    private val pullLenght = Utils.dp2px(10f)

    /**
     * 图形测量会经过多次，
     * 当测量结果和上次结果一样的时候没有必要再次做一些不一样的计算
     * 只有当结果有变化的时候回走这个方法
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var currentAngle = 0f
        colors.forEachIndexed { index, color ->
            paint.color = color
            if (index == pullIndex) {
                canvas.save()
                canvas.translate(
                    Math.cos(Math.toRadians((currentAngle + angles[index] / 2).toDouble())).toFloat() * pullLenght,
                    Math.sin(Math.toRadians((currentAngle + angles[index] / 2).toDouble())).toFloat() * pullLenght
                )
            }
            canvas.drawArc(bounds, currentAngle, angles[index], true, paint)
            if (index == pullIndex)
                canvas.restore()
            currentAngle += angles[index]
        }

    }

}