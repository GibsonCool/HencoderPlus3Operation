package com.example.doublex.a16_multi_touch.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.example.doublex.a16_multi_touch.Utils

/**
 * 多点各自作用。实现简单画板
 */
class MultiTouchView3 : View {


    private val imageWidth = Utils.dp2px(200f)
    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = Utils.dp2px(4f)
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }
    }
    private var bitmap: Bitmap
    private val paths = SparseArray<Path>()


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        bitmap = Utils.getAvatar(resources, imageWidth.toInt())
    }

    /**
     *  多点记录，在每个 DOWN、POINTER_DOWN 事件中记录下每个 pointer 的 id，
     *  在 MOVE 事件中使⽤用 id 对它们进⾏行行跟踪
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            //根据当前时间的actionIndex 获取对应 PointerID。 通过PointerID创建一一对应path放入集合中
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_DOWN -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                val path = Path()
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
                paths.append(pointerId, path)
            }

            //遍历集合-->根据pointerID找到对应的path进行路径记录
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until event.pointerCount) {
                    val pointerId = event.getPointerId(i)
                    val path = paths.get(pointerId)
                    path.lineTo(event.getX(i), event.getY(i))
                }
            }
            //更具PointerID找到对应path移除
            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_UP -> {
                val pointerId = event.getPointerId(event.actionIndex)
                paths.remove(pointerId) //这里应该是要使用复用池，然后调用path.reset(),
            }

        }
        invalidate()
        return true
    }

    /**
     * 多点绘制
     */
    override fun onDraw(canvas: Canvas) {
        for (i in 0 until paths.size()) {
            val path = paths.valueAt(i)
            canvas.drawPath(path, paint)
        }
    }
}