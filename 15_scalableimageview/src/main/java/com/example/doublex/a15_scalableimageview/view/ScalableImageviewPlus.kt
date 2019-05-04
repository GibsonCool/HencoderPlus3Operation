package com.example.doublex.a15_scalableimageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import com.example.doublex.a15_scalableimageview.Utils

/**
 * 可双击缩放并滑动查看的view
 *  支持双指缩放
 */
class ScalableImageviewPlus : View {


    private val tag = "doublex"
    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val imageWidth = Utils.dp2px(300f).toInt()
    val bitmap: Bitmap  by lazy { Utils.getAvatar(resources, imageWidth) }

    //设置初始偏移位置，在整个view中居中
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    //滑动偏移
    private var offsetX = 0f
    private var offsetY = 0f

    //计算内贴边(图片放大到第一次左右或者上下某一处和View正好重合)和外贴边缩放的值，
    private var smallScale = 0f
    private var bigScale = 0f
    private var overScaleFactor = 2.5f      //设置默认要超出最大的范围，在bitScale的基础上放大
    private var isBig = false       //记录当前状态是大图还是小图

    //用于做缩放动画的分数变量
    var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }
    val scaleAnimator: ObjectAnimator by lazy { ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale) }

    //touch事件-->手势识别器
    private var detector: GestureDetectorCompat
    private var scroller: OverScroller

    private val gestureListener = HenGestureListener()
    private val henFlingRunner = HenFlingRunner()

    //缩放监听器
    private var scaleDetector: ScaleGestureDetector
    private val henScaleListener = HenScaleListener()


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        detector = GestureDetectorCompat(context, gestureListener)
        scroller = OverScroller(context)
        scaleDetector = ScaleGestureDetector(context, henScaleListener)
    }

    //将时间传递交给手势识别器处理
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        /**
         * 处理事件冲突？是交由点击滑动处理还是交由缩放处理
         *  1、先交给缩放监听处理
         *  2、判断是否还在进行缩放手势过程
         *  3、如果没有在缩放了，交由滑动点击事件监听处理
         */
        var result = scaleDetector.onTouchEvent(event)
        if (!scaleDetector.isInProgress) {
            result = detector.onTouchEvent(event)
        }
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f

        if (bitmap.width / bitmap.height > width / height) {
            smallScale = (width / bitmap.width).toFloat()
            bigScale = (height / bitmap.height).toFloat()
        } else {
            smallScale = (height / bitmap.height).toFloat()
            bigScale = (width / bitmap.width).toFloat()
        }
        bigScale *= overScaleFactor
        currentScale = smallScale
        Log.e(tag, "smallScale:$smallScale    bigScale:$bigScale")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        //进行滑动偏移设置
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }


    /**
     * 惯性滑动任务
     */
    inner class HenFlingRunner : Runnable {
        override fun run() {
            //每次先更新计算下偏移的值,返回值动画是否还在进行中
            if (scroller.computeScrollOffset()) {
                /**
                 *  初始偏移为0 ，所以滑动偏移就直接可以使用scrooler的偏移
                 */
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                //在下一帧去执行
                postOnAnimation(this)
            }
        }
    }


    /**
     *  处理双击，惯性滑动事件
     */
    inner class HenGestureListener : GestureDetector.SimpleOnGestureListener {
        constructor() : super()

        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        //一般为了后面的其他一些时间可以处理，这里默认返回true
        override fun onDown(e: MotionEvent?): Boolean = true

        /**
         * 支持惯性滑动，进行业务逻辑处理
         */
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (isBig) {
                //设置并计算scroll
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    //给出图片能滑动的最大、最小值
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt(),
                    //超出回弹距离
                    0,
                    0
                )

                postOnAnimation(henFlingRunner)
            }
            return false
        }

        /**
         * distanceX:上一个点和这一点的偏移量（oldX-newX)
         * distanceY同理
         */
        override fun onScroll(
            downEvent: MotionEvent?,
            event: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            //大图模式下支持滑动查看
            if (isBig) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isBig = !isBig
            if (isBig) {
                /**
                 * 实现从点击位置处进行放大，进行偏移计算
                 */
                //相对于图片中心位置手指点击的偏移量 - 手指点击位置在图片上因为放大后产生的偏移
                offsetX = (e.x - width / 2f) - (e.x - width / 2f) * (bigScale / smallScale)
                offsetY = (e.y - height / 2f) - (e.y - height / 2f) * (bigScale / smallScale)
                fixOffsets()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return false
        }


        /**
         * 边界修复，让滑动不能给你超过图片放大后边界
         */
        private fun fixOffsets() {
            offsetX = Math.min(offsetX, (bitmap.width * bigScale - width) / 2)
            offsetX = Math.max(offsetX, -(bitmap.width * bigScale - width) / 2)
            offsetY = Math.min(offsetY, (bitmap.height * bigScale - height) / 2)
            offsetY = Math.max(offsetY, -(bitmap.height * bigScale - height) / 2)

        }
    }

    /**
     * 缩放监听器
     */
    inner class HenScaleListener : ScaleGestureDetector.OnScaleGestureListener {
        //初始放缩系数
        var initialScal: Float = 0f
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            initialScal = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // detector.scaleFactor 手指进行的放缩系数。
            currentScale = initialScal * detector.scaleFactor
            invalidate()
            return false
        }
    }
}