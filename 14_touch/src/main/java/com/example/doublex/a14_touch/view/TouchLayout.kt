package com.example.doublex.a14_touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup

class TouchLayout : ViewGroup {
    var downY = 0f
    val SLOP = 80

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun shouldDelayChildPressedState(): Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        val delta = ev.y-downY
        return Math.abs(delta) >= SLOP
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }
}
