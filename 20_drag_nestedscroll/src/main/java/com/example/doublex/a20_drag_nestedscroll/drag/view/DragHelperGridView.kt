package com.example.doublex.a20_drag_nestedscroll.drag.view

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * ViewDragHelper实现拖拽
 *
 *  1、进行简单的测量和布局
 *
 *  2、创建ViewDragHelpter并将事件拦截和事件处理交由dragHelper接管
 *
 *  3、viewDragHelper的拖拽原理实时修改被拖拽子view的 mLeft, mTop, mRight, mBottom 值
 *
 *  4、在ViewDragHelper.Callback的回调中去进行按需求干预，初始化，释放处理
 */
class DragHelperGridView : ViewGroup {

    companion object {
        //GridView行列数
        const val COLUMNS = 2
        const val ROWS = 3
    }

    private var dragHelper: ViewDragHelper

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        dragHelper = ViewDragHelper.create(this, DragCallBack())
    }

    /**
     * 根据默认的行列数量，计算测量子view的宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spaceWidth = MeasureSpec.getSize(widthMeasureSpec)
        val spaceHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = spaceWidth / COLUMNS
        val childHeight = spaceHeight / ROWS

        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )

        setMeasuredDimension(spaceWidth, spaceHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for (i in 0 until count) {
            val child = getChildAt(i)
            childLeft = i % COLUMNS * childWidth
            childTop = i / COLUMNS * childHeight
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    inner class DragCallBack : ViewDragHelper.Callback() {
        private var captureLeft: Int = 0
        private var captureTop: Int = 0
        /**
         * 这是唯一的一个抽象方法，需要自己实现的。返回值表示是否捕捉这个 view 的拖拽事件。
         * 这个方法会调用多次，哪怕这个 view 已经被捕捉过了，在下一次开始拖拽的时候，还是会回调这个方法
         * 如果只想对 ViewGroup 内的特定的 view 进行拖拽的处理，只需要返回类似于 child == mDragView 这样的形式就行了。
         */
        override fun tryCaptureView(p0: View, p1: Int): Boolean = true


        /**
         * 这个方法约束了 View 在水平方向上的运动。该方法默认是返回0的，所以一般都是需要重写的。
         * 这个方法有三个参数：
         *      第一个 View 自然就是拖动的 View；
         *      第二个参数 left，指的是拖动的 View 理论上将要滑动到的水平方向上的值；
         *      第三个参数 dx 可以理解为滑动的速度，单位是 px 每秒。返回值是水平方向上的实际的x坐标的值。
         */
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int = left

        /**
         * 垂直方向上的约束，同clampViewPositionHorizontal类似
         */
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int = top

        /**
         * 当CaptureView 被捕获时的回调，用于记录一些初始化操作
         */
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            capturedChild.elevation = elevation + 1
            captureLeft = capturedChild.left
            captureTop = capturedChild.top
        }

        /**
         * 当ViewDragHelper状态发生变化时回调（IDLE,DRAGGING,SETTING）
         */
        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE) {
                if(dragHelper.capturedView!=null){
                    dragHelper.capturedView!!.elevation = dragHelper.capturedView!!.elevation-1
                }

            }
        }

        /**
         * 当captureview的位置发生改变时回调
         */
        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
        }

        /**
         * 这个方法在 View 释放的时候调用，就是说这个 View 已经不再被拖拽的时候调用。
         * View 已经不再被拖拽的时候，该 View 可能并没有停止滑动，
         *      xvel 和 yvel 表示的是此时该 View 在水平和竖直方向上的速度，单位是px/s
         */
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            // 当手指拖拽释放时候，设置捕获的view应该回到原位置，然后触发下一帧到来的时候刷新界面
            dragHelper.settleCapturedViewAt(captureLeft, captureTop)
            postInvalidateOnAnimation()
        }
    }

    /**
     * 通过dragHelper判断捕获的view计算是否回到指定位置，然后不断刷新界面
     */
    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            //ViewCompat通过API判断，封装了版本不兼容的方法在不同版本上的实现
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}