package com.example.doublex.a10_text_and_transformation.view

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.doublex.a10_text_and_transformation.Utils

/**
 * 图文绘制难点：
 *      1、paint.breakText() 在给出的宽度上限的前提下测量文字的宽度。如果文字的宽度超出了上限，就在临近的位置截断文字
 *                           并返回截断的个数。
 *
 *
 */
class ImageTextView : View {

    val textPain by lazy { TextPaint() }
    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val imageWidth = Utils.dp2px(150f)
    val imagePaddingTop = Utils.dp2px(80f)
    val measuredWith = floatArrayOf()
    val metrics = Paint.FontMetrics()
    val image: Bitmap
    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor.";

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        //设置绘制文字大小，字体
        paint.textSize = Utils.dp2px(20f)
        paint.getFontMetrics(metrics)
        image = Utils.getAvatar(resources, imageWidth.toInt())

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(image, width - imageWidth, imagePaddingTop, paint)

        //TextView 内部也是使用这种方式来做多行文字显示的
//        val staticLayout = StaticLayout(text,textPai n,width, Layout.Alignment.ALIGN_NORMAL,1f,0f,false)
//        staticLayout.draw(canvas)
        val length = text.length
        var start = 0   //每一行文字的起始位置
        var count = 0   //每一行文字的截取长度
        var yOffset = paint.fontSpacing    //每一行文字的Y坐标偏移量
        var useableWidth = 0f   //判断每一行文字可用长度范围
        while (start < length) {
            val textTop = yOffset + metrics.ascent
            val textBottomBar = yOffset + metrics.descent

            //判断这行文字上下距离是或否在图片上下距离内
            if (textTop > imagePaddingTop && textTop < imagePaddingTop + imageWidth ||
                textBottomBar > imagePaddingTop && textBottomBar < imageWidth + imagePaddingTop
            ) {
                useableWidth = width - imageWidth
            } else {
                useableWidth = width.toFloat()
            }


            count = paint.breakText(text, start, length, true, useableWidth, measuredWith)
            canvas.drawText(text, start, start + count, 0f, yOffset, paint)

            start += count
            yOffset += paint.fontSpacing
        }


    }

}