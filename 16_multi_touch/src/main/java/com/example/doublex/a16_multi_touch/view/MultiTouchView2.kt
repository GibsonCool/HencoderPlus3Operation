package com.example.doublex.a16_multi_touch.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
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

    // 记录追踪的 Point id
    private var trackingPointerId = 0

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        bitmap = Utils.getAvatar(resources, imageWidth.toInt())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                //当view接受到第一个point事件，取第一个的Point id
                trackingPointerId = event.getPointerId(0)

                downX = event.x
                downY = event.y
                imageOffsetX = offsetX
                imageOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {

                val index = event.findPointerIndex(trackingPointerId)
                offsetX = imageOffsetX + event.getX(index) - downX
                offsetY = imageOffsetY + event.getY(index) - downY
                invalidate()
            }

            // 额外⼿手指按下(按下之前已经有别的⼿手指触摸到 View)
            MotionEvent.ACTION_POINTER_DOWN -> {
                //获取多点时候当前事件Pointer对应的index
                val actionIndex = event.actionIndex
                //根据index 更新当前追踪的Pointer id
                trackingPointerId = event.getPointerId(actionIndex)

                //以当前触摸点pointer 的 index 为准更新偏移量和初始值
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                imageOffsetX = offsetX
                imageOffsetY = offsetY
            }

            //有⼿手指抬起，但不不是最后⼀一个(抬起之后，仍然还有别的⼿手指在触 摸着 View)
            MotionEvent.ACTION_POINTER_UP -> {
                //获取当前抬起手指pointer index 和 id
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                /**
                 * 如果当前取消pointer id 是我们之前一直使用追踪的Pointer id 。
                 * 这个Pointer将移除，则需要从新寻找更新trackingPointerId
                 */
                if (pointerId == trackingPointerId) {
                    /**
                     * 更新的 trackingPointerId 我们取最后一个
                     * 如果当前抬起pointer的index 已经是最后一个，那就往前在取一个
                     */
                    val newIndex = event.pointerCount - (if (actionIndex == event.pointerCount - 1) 2 else 1)

                    //以当前触摸点pointer 的 index 为准更新偏移量和初始值
                    trackingPointerId = event.getPointerId(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    imageOffsetX = offsetX
                    imageOffsetY = offsetY
                }
            }

        }

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }
}