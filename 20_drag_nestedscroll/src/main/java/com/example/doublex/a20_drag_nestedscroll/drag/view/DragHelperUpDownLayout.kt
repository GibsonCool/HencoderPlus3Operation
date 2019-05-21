package com.example.doublex.a20_drag_nestedscroll.drag.view

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import com.example.doublex.a20_drag_nestedscroll.R

/**
 *  这是一个通过ViewDragHelper来快捷实现上下拖动切换的效果
 *
 *      默认不实现 clampViewPositionHorizontal 始终返回0，限制左右滑动
 *
 */
class DragHelperUpDownLayout : FrameLayout {

    private var dragHelper: ViewDragHelper
    private var dragListener = DragCallBack()
    private var viewConfiguration: ViewConfiguration

    private lateinit var dragView: View

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        dragHelper = ViewDragHelper.create(this, dragListener)
        viewConfiguration = ViewConfiguration.get(context)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        dragView = findViewById(R.id.dragView)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    inner class DragCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(p0: View, p1: Int): Boolean = p0 == dragView

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int = top

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {

            val finalTop = if (Math.abs(yvel) > viewConfiguration.scaledMinimumFlingVelocity) {
                if (yvel > 0) height - releasedChild.height else 0
            } else {
                if (releasedChild.top < height - releasedChild.bottom) 0 else height - releasedChild.height
            }

            dragHelper.settleCapturedViewAt(0, finalTop)
            postInvalidateOnAnimation()
        }

    }
}