package com.example.doublex.a13_layout.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.example.doublex.a13_layout.Utils

/**
 * 完全自定义view的尺寸
 *      1、重写onMeasure
 *
 *      2、按照自己的期望计算出尺寸
 *
 *      3、用resolveSize()或者resolveSizeAndState()修正结果
 *           （其实就是迎合父View的对子view的尺寸要求，不要超出要求范围。）
 *
 *      4、调用setMeasuredDimension 重新保存尺寸
 */
class CircleView : AppCompatImageView {
    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val radius = Utils.dp2px(80f)
    private val circlePadding = Utils.dp2px(20f)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paint.color = Color.RED
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((circlePadding + radius) * 2).toInt()
        var measureWidth= resolveSize(size,widthMeasureSpec)
        var measureHeight = resolveSize(size,heightMeasureSpec)


        setMeasuredDimension(measureWidth, measureHeight)
    }

    /**
     * 对尺寸进行修正，----》系统api封装和提供了同样的功能， resolveSize
     */
    private fun fixSize(measureSpec: Int,  size: Int):Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val modeSize = MeasureSpec.getSize(measureSpec)
        return  when (mode) {
            //精确模式。按照具体尺寸来设置
            MeasureSpec.EXACTLY -> modeSize
            //最大模式：控制尺寸范围在父空间给出的最大可用空间内
            MeasureSpec.AT_MOST -> if (size > modeSize) modeSize else size
            //未指定模式：自由发挥，不受限制。
            MeasureSpec.UNSPECIFIED -> size
            else -> size
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        canvas.drawCircle(circlePadding + radius, circlePadding + radius, radius, paint)
    }
}