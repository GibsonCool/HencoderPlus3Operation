package com.example.doublex.a13_layout.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * 简单自定义view的尺寸
 *      1、重写onMeasure
 *
 *      2、获取测量的尺寸 measureHeight  measureWidth
 *
 *      3、按照自己的期望计算出尺寸
 *
 *      4、调用setMeasuredDimension 重新保存尺寸
 */
class SquareImageView : AppCompatImageView {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /**
         *
         * 通过获取测量的宽高(必须在super.onMeasure之后调用)，实际就是Xml中设置的200 300 然后取最大值，重新保存测量宽高的值
         */
        val size = Math.max(measuredWidth,measuredHeight)
        setMeasuredDimension(size,size)
    }
}