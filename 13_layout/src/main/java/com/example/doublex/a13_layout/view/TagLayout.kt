package com.example.doublex.a13_layout.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * 自定义 TagLayout
 *
 *  1、重写onMeasure（难点、重点）
 *      1.1 遍历每个子view，用measureChildWithMargins()测量子view
 *          （测量子view不使用measureChild以及为什么要重写generateLayoutParams的原因查看 https://www.jianshu.com/p/5fbb1ce3c7f0）
 *
 *      1.2 有些子view需要计算测量，比如换行处。测量完成后得出子view的实际位置和尺寸暂时保存。
 *
 *      1.3 根据测量出的所有子view的尺寸位置，计算出viewGroup的尺寸，并调用setMeasuredDimension（）保存
 *
 *
 *  2、重写onLayout
 *      遍历子view，并且调用上面测量好的每隔view的位置进行子view的layout布局
 *
 */
class TagLayout : ViewGroup {
    val childrenBounds = arrayListOf<Rect>()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    /**
     * 重写此方法，返回false，表示我这个ViewGroup不是可滑动的
     * 如果是true表示可滑动，对自己内部的view的触摸会有一个100毫秒的延迟，这样做可优化一点
     * 具体可查看View 的 onTouchEvent事件中的 isInScrollingContainer=true --> postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());
     */
    override fun shouldDelayChildPressedState(): Boolean = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //viewgroup的宽度和模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        //记录最终测量完毕后已经使用的宽高
        var widthUsed = 0
        var heightUsed = 0

        //记录实时测量中行高、行宽使用的情况
        var lineWidthUsed = 0
        var lineHeight = 0

        /**
         * 遍历子view进行测量
         */
        for (i in 0 until childCount) {

            val child = getChildAt(i)

            /**
             * 次方法会调用getChildMeasureSpec，根据传入的父view的measureMode和子view的设置的LayoutParams综合得出子view的尺寸限制childMeasure
             *
             * 然后调用子view的child.measure(childWidthMeasureSpec, childHeightMeasureSpec)让子view自我测量得出位置尺寸
             *
             */
            // 第一次测量 widthUsed都是0，默认没有使用过，保证给子view最大可用空间去测量
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)


            // 如果父view的宽度要求不是不限制，并且行已经使用的宽度加上当前第一次测量出来的child实际需要的宽度大于父view的宽度
            // 则进行换行第二次测量并设置改变child的位置尺寸到下一行
            if (widthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.measuredWidth > widthSize) {
                lineWidthUsed = 0
                heightUsed += lineHeight
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }
            val childBounds = if (childrenBounds.size <= i) {
                Rect().also {
                    childrenBounds.add(it)
                }
            } else {
                childrenBounds[i]
            }

            // 进过测量后的重新设置次child的位置信息
            childBounds.set(
                lineWidthUsed,
                heightUsed,
                lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            //计算和累加已使用的宽高值
            lineWidthUsed += child.measuredWidth
            widthUsed = Math.max(lineWidthUsed, widthUsed)
            lineHeight = Math.max(lineHeight, child.measuredHeight)      //如果只有一行的时候取子view中的最高值

        }

        //计算完所有子view的测量得出并重新设置 宽高
        val viewGroupMeasureWidth = widthUsed
        heightUsed += lineHeight
        val viewGroupMeasureHeight = heightUsed
        setMeasuredDimension(viewGroupMeasureWidth, viewGroupMeasureHeight)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val bounds = childrenBounds[i]
            getChildAt(i).layout(bounds.left, bounds.top, bounds.right, bounds.bottom)
        }
    }


    /**
     * 这个方法主要是用于父容器添加子View时调用。用于生成和此容器类型相匹配的布局参数类
     * 此处为了方便使用   measureChildWithMargins() 方法
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}