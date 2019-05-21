package com.example.doublex.a20_drag_nestedscroll.drag.view

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup

/**
 * OnDragListener实现拖拽
 *
 *  1、通过view startDrag()来启动拖拽
 *
 *  2、用setOnDragListener来监听
 *          OnDraglistener内部只有一个方法： onDrag()
 *          onDragEvent()方法也会收到拖拽回调（界面中的每个view都会收到
 */
class DragListenerGridView : ViewGroup {


    private lateinit var draggedView: View
    private val viewConfiguration: ViewConfiguration
    private val dragListener = HenDragListener()

    private val orderedChildren = ArrayList<View>()
    private var childWidth:Int = 0
    private var childHeight:Int = 0


    companion object {
        //GridView行列数
        const val COLUMNS = 2
        const val ROWS = 3
        const val TAG = "doublex"
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        viewConfiguration = ViewConfiguration.get(context)
        setChildrenDrawingCacheEnabled(true)
    }


    /**
     * 根据默认的行列数量，计算测量子view的宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spaceWidth = MeasureSpec.getSize(widthMeasureSpec)
        val spaceHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = spaceWidth / COLUMNS
        val childHeight = spaceHeight / ROWS

        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )

        setMeasuredDimension(spaceWidth, spaceHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var childLeft: Float
        var childTop: Float
         childWidth = width / COLUMNS
         childHeight = height / ROWS
        for (i in 0 until count) {
            val child = getChildAt(i)
            childLeft = (i % COLUMNS * childWidth).toFloat()
            childTop = (i / COLUMNS * childHeight).toFloat()
            child.layout(0, 0, childWidth, childHeight)
            child.translationX = childLeft
            child.translationY = childTop

        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            orderedChildren.add(child)
            child.setOnLongClickListener {
                //设置响应拖拽事件的view为 draggedView
                draggedView = it
                ViewCompat.startDragAndDrop(it, null, DragShadowBuilder(it), it, 0)
                false
            }
            //通过给子view设置监听回调，或者在 下面onDragEvent回调中都可以进行处理
            child.setOnDragListener(dragListener)
        }
    }

    /**
     * 这里的效果和给view设置OnDragListener一样
     *  不通的是界面这个中的每个view 都会收到这个回调
     */
    override fun onDragEvent(event: DragEvent?): Boolean {
        return super.onDragEvent(event)
    }

    inner class HenDragListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    val isDragView = event.localState == v
                    Log.e(TAG, "开始拖拽  isDragView:$isDragView")
                    if (isDragView) {
                        v.visibility = View.INVISIBLE
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    Log.e(TAG, "拖拽的view进入监听的view时  ${event.localState == v}")
                    if (event.localState != v) {
                        sort(v)
                    }
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.e(TAG, "拖拽的view离开监听的view时  ${event.localState == v}")
                }
                DragEvent.ACTION_DRAG_LOCATION -> {

                    Log.e(TAG, "拖拽的view在监听view中的位置:x =${v.x},y=${v.y}")
                }
                DragEvent.ACTION_DROP -> {

                    Log.e(TAG, "释放拖拽的view")
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    val isDragView = event.localState == v
                    Log.e(TAG, "结束拖拽  isDragView:$isDragView")
                    if (isDragView) {
                        v.visibility = View.VISIBLE
                    }

                }
            }

            return true
        }

    }

    fun sort(targetView: View) {
        var targetIndex = -1
        var draggedIndex = -1

        //通过遍历确认 目标和当前拖拽view 的 index
        orderedChildren.forEachIndexed { index, view ->
            if (targetView == view)
                targetIndex = index

            if (draggedView == view)
                draggedIndex = index
        }

        //根据两个index不相等，说明发生了移动需要重排位置
        if (targetIndex != draggedIndex) {
            orderedChildren.removeAt(draggedIndex)
            orderedChildren.add(targetIndex, draggedView)
        }


        //排列完位置后按位置从新进行偏移动画
        var childLeft: Float
        var childTop: Float
        orderedChildren.forEachIndexed { index, view ->
            childLeft = (index % 2 * childWidth).toFloat()
            childTop = (index / 2 * childHeight).toFloat()
            view.animate()
                .translationX(childLeft)
                .translationY(childTop)
                .duration = 500
        }
    }
}