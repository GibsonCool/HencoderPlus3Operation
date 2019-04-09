package com.example.doublex.hencoderplus3operation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.doublex.hencoderplus3operation.R
import com.example.doublex.hencoderplus3operation.Utils

class AvatarView : View {
    private val width = Utils.dp2px(300f)
    private val padding = Utils.dp2px(40f)
    private val borderWidth = Utils.dp2px(10f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val avatar by lazy { getAvatar(width.toInt()) }

    private val xFermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private val cut = RectF()
    private val border = RectF()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cut.set(padding, padding, padding + width, padding + width)
        border.set(
            padding - borderWidth,
            padding - borderWidth,
            padding + width + borderWidth,
            padding + width + borderWidth
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //先画个黑圈
        canvas.drawOval(border,paint)


        //设置离屏缓冲相当于ps里面新建图层里面取操作，可提高硬件效率
        //这里也必须这样子设置了xfermode才生效
        val saved = canvas.saveLayer(cut, paint)

        //画圆
        canvas.drawOval(cut, paint)

        //设置Xfermode，是后面画的部分值显示圆的范围内
        paint.xfermode = xFermode

        //画头像
        canvas.drawBitmap(avatar, padding, padding, paint)

        //清除效果，将绘制缓冲贴回画布上
        paint.xfermode = null
        canvas.restoreToCount(saved)

    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options().also {
            it.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, R.drawable.avatar_doublex, it)
            it.inJustDecodeBounds = false
            it.inDensity = it.outHeight
            it.inTargetDensity = width
        }

        return BitmapFactory.decodeResource(resources, R.drawable.avatar_doublex, options)
    }
}