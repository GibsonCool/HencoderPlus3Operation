package com.example.doublex.hencoderplus3operation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.doublex.hencoderplus3operation.Utils

class DashBoard : View {
    private val RADIUS = Utils.dp2px(150f)
    //指正长度
    private val ZHIZHENGLENG = Utils.dp2px(100f)
    //仪表盘开角角度
    private val ANGLE = 120f
    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val dash by lazy { Path() }
    // 记录弧度周长用的path
    private val pathPerimeter by lazy { Path() }

    private lateinit var dashPathEffect: PathDashPathEffect

    private lateinit var pathMeasure: PathMeasure

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(3f)

        //刻度的长宽
        dash.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(10f), Path.Direction.CCW)

    }

    /**
     * 图形测量会经过多次，
     * 当测量结果和上次结果一样的时候没有必要再次做一些不一样的计算
     * 只有当结果有变化的时候回走这个方法
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        pathPerimeter.addArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2 + RADIUS,
            90 + ANGLE / 2,
            360 - ANGLE
        )


        pathMeasure = PathMeasure(pathPerimeter, false)
        val pathLength = pathMeasure.length

        //设置路径的效果，例如虚线、实线、不规则曲线等
        //这里使用虚线效果，虚线每段画成刻度条来表示仪表盘刻度
        dashPathEffect = PathDashPathEffect(
            dash,
            (pathLength - Utils.dp2px(2f)) / 20,     //这里填间距， 用周长除以总刻度数
            0f,      //起始偏移度
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /**
         * 1。画原图形，盘
         */

        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2 + RADIUS,
            90 + ANGLE / 2, //起始角度 = 90+夹角的一半
            360 - ANGLE,    //需要画过的角度= 360-其实夹角角度
            false, //是否闭合到中心:false 弧形  true 扇形
            paint
        )

        /**
         * 2。画刻度
         */


        paint.pathEffect = dashPathEffect

        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2 + RADIUS,
            90 + ANGLE / 2, //起始角度 = 90+夹角的一半
            360 - ANGLE,    //需要画过的角度= 360-其实夹角角度
            false, //是否闭合到中心:false 弧形  true 扇形
            paint
        )
        //绘制完，恢复还原路径效果
        paint.pathEffect = null

        /**
         * 3。画指针
         */
        canvas.drawLine(
            width / 2f,
            height / 2f,
            width / 2f + Math.cos(Math.toRadians(getAngleForMark(5))).toFloat() * ZHIZHENGLENG,
            height / 2f + Math.sin(Math.toRadians(getAngleForMark(5))).toFloat() * ZHIZHENGLENG,
            paint
        )

    }

    //根据当前刻度位置，获取其角度
    fun getAngleForMark(mark: Int): Double = (90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark).toDouble()
}