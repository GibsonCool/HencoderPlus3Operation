package com.example.doublex.a16_multi_touch.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.doublex.a16_multi_touch.Utils

/**
 * 多点共同作用型
 */
class MultiTouchView2 : View {


    private val imageWidth = Utils.dp2px(200f)
    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var bitmap: Bitmap

    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var imageOffsetX: Float = 0f
    private var imageOffsetY: Float = 0f


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        bitmap = Utils.getAvatar(resources, imageWidth.toInt())
    }

    /**
     * 多点共同作用的关键点，就是找到多点的中心坐标
     *      中心点 = (point+point+...) / pointCount
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {

        var sumX = 0f
        var sumY = 0f
        var pointerCount = event.pointerCount

        /**
         * 判断是PointerUp事件并且不把这次事件参数作用于中心点上
         */
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        for (i in 0 until pointerCount) {
            if (!(isPointerUp && event.actionIndex == i)) {
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        //减去PointerUp的事件个数
        if (isPointerUp) pointerCount--

        //共同作用中心点坐标
        val focusX = sumX / pointerCount
        val focusY = sumY / pointerCount

        when (event.actionMasked) {
            //无论多点或者第一次的down事件都需要更正坐标
            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_DOWN -> {
                downX = focusX
                downY = focusY
                imageOffsetX = offsetX
                imageOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                offsetX = imageOffsetX + focusX - downX
                offsetY = imageOffsetY + focusY - downY
                invalidate()
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }
}