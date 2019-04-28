package com.example.doublex.a14_touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * event.action 与 event.actionMasked的区别
 *      action:是比较老的方法
 *      actionMasked:是新方法，多了对多点触控的支持，事件也多了 MotionEvent.ACTION_POINTER_DOWN 和 MotionEvent.ACTION_POINTER_UP
 */
class TouchView : View {

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        MotionEvent.ACTION_POINTER_DOWN
        MotionEvent.ACTION_POINTER_UP
        if (event.actionMasked == MotionEvent.ACTION_UP)
            performClick()

        return true
    }
}