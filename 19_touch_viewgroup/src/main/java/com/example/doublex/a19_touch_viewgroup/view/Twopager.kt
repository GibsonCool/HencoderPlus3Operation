package com.example.doublex.a19_touch_viewgroup.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller

class Twopager : ViewGroup {

    private var downX: Float = 0.0f
    private var downY: Float = 0.0f
    private var downScrollX: Float = 0.0f
    private var scrolling = false
    private var minVelocity: Float
    private var maxVelocity: Float
    //系统中关于view的各种特性常量记录对象
    private var viewConfiguration: ViewConfiguration
    //用于控件移动轨迹辅助计算类
    private var overScroller: OverScroller
    //速度检测器
    private var velocityTracker = VelocityTracker.obtain()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        overScroller = OverScroller(context)
        viewConfiguration = ViewConfiguration.get(context)
        minVelocity = viewConfiguration.scaledMinimumFlingVelocity.toFloat()
        maxVelocity = viewConfiguration.scaledMaximumFlingVelocity.toFloat()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        var childRight = width
        var childBottom = height

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    /**
     * 拦截触摸反馈判断，初始化一些参数
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var result = false
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                if (!scrolling) {
                    val dx = downX - ev.x
                    //判断移动距离是否满足滚动要求
                    if (Math.abs(dx) > viewConfiguration.scaledPagingTouchSlop) {
                        scrolling = true
                        //满足要求表示当前需要执行滚动，则请求父类不要拦截事件，由我自己来处理
                        parent.requestDisallowInterceptTouchEvent(true)
                        result = true
                    }
                }
            }
        }

        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                //scrollX 返回的是view已经滑动出屏幕外的距离
                downScrollX = scrollX.toFloat()
            }
            //跟随手指滑动
            MotionEvent.ACTION_MOVE -> {
                var dx = downX - event.x + downScrollX
                //进行距离矫正，防止左右滑动超出
                dx = when {
                    dx > width -> width.toFloat()
                    dx < 0 -> 0F
                    else -> dx
                }
                scrollTo(dx.toInt(), 0)
            }
            //手指抬起的时候，进行后续平滑过渡处理
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity)
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                /**
                 * 根据上面的速度检测器，获取水平方向的飞速滑动的速率值，
                 * 如果当前超过最小速率值就进行翻页
                 * 否则判断当前已经滑动距离是否超过一半宽度来确定是否翻页
                 */
                val targetPage = when {
                    Math.abs(vx) < minVelocity -> {
                        if (scrollX > width / 2) 1 else 0
                    }
                    else -> {
                        if (vx < 0) 1 else 0
                    }
                }

                val scrollDistance = if (targetPage == 1) width - scrollX else -scrollX
                /**
                 * 根据最终确定滑动页计算出手指提起后需要自主完成的滑动距离
                 * 然后使用滚动辅助类计算滚动的数值变化并调用view刷新
                 */
                overScroller.startScroll(scrollX, 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }


        return true
    }

    /**
     * 当view从新绘制的时候会调用次方法，
     * 承接 MotionEvent.ACTION_UP 中的界面刷新
     */
    override fun computeScroll() {
        //通过滚动辅助类判断是否已经完成滚动，没有则进行下一次滚动并在此循环次操作
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }

}