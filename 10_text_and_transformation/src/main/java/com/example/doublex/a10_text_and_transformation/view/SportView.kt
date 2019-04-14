package com.example.doublex.a10_text_and_transformation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.doublex.a10_text_and_transformation.Utils

/**
 * 文字绘制难点：
 *
 *  1、垂直居中(默认文字是按照baseline基线居中的，往往在视觉上不是居中，需要减去偏移量来达到视觉居中)
 *
 *      1.1 如果文字基本不会变固定的可以使用getTextBound获取文字绘制矩形区域来计算偏移量
 *
 *      2.2 如果文字可变，由于不同文字的出现会导致baseline变化，所以通过getFontMetrics()来处理偏移量避免出现文字上下跳动问题
 *
 *
 *  2、贴边
 *      贴边主要是也是铜鼓getTextBound获取文字绘制区域，然后减去需要贴边方向的值比如左边 -bounds.left
 */
class SportView : View {

    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    //圆环宽度
    private val ringWidth = Utils.dp2px(20f)
    //圆环半径
    private val radius = Utils.dp2px(150f)
    private val circleColor = Color.parseColor("#90A4AE")
    private val highlightColor = Color.parseColor("#FF4081")
    private val textColor = Color.RED

    private val bounds = Rect()
    private val metrics = Paint.FontMetrics()


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        //设置绘制文字大小，字体
        paint.textSize = Utils.dp2px(60f)
        paint.typeface = Typeface.createFromAsset(context.assets, "Quicksand-Regular.ttf")

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /**
         *  绘制圆环
         */
        paint.style = Paint.Style.STROKE
        paint.color = circleColor
        paint.strokeWidth = ringWidth
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)

        /**
         * 绘制进度条
         */
        paint.color = highlightColor
        //设置线帽样式  Cap.ROUND(圆形线冒)  Cap.SQUARE(方形线冒)  Cap.BUTT(无线冒)
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - radius,
            height / 2f - radius,
            width / 2f + radius,
            height / 2f + radius,
            100f,
            250f,
            false,
            paint
        )
        paint.strokeCap = Paint.Cap.BUTT


        /**
         * 绘制文字
         */
        val text = "DoubleX"
        paint.style = Paint.Style.FILL
        paint.color = textColor
        paint.textAlign = Paint.Align.CENTER

        //高度减去文字基于基线到所在矩形区域中心的偏移量来达到居中
        //这种方式所达到的文字居中是准确的，但是如果文字是变换的，由于一些文字的不同会导致文字'基线'
        //的改变，从而中心也会变。导致文字上下可能粗线跳跃，不是永远都在同一中心的问题

        //Paint.getTextBounds (获得文字所在矩形区域，可以得到宽高)
        //textPain.getTextBounds(text, 0, text.length, bounds)
        //val offset = (bounds.top + bounds.bottom) / 2f

        //关于字体度量FontMetrics可以查看这里：https://blog.csdn.net/flyeek/article/details/43934945
        paint.getFontMetrics(metrics)
        val offset = (metrics.ascent + metrics.descent) / 2f

        canvas.drawText(text, width / 2f, height / 2f - offset, paint)


        /**
         * 绘制文字贴边
         */
        paint.textAlign = Paint.Align.LEFT
        paint.textSize =Utils.dp2px(100f)
        paint.getTextBounds(text,0,text.length,bounds)
        canvas.drawText(text,-bounds.left.toFloat(),-bounds.top.toFloat(),paint)

        paint.textAlign = Paint.Align.LEFT
        paint.textSize =Utils.dp2px(30f)
        paint.color = Color.GREEN
        paint.getTextBounds(text,0,text.length,bounds)
        canvas.drawText(text,-bounds.left.toFloat(),-bounds.top.toFloat(),paint)

    }

}