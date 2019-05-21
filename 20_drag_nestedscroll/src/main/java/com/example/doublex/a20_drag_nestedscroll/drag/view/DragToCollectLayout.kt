package com.example.doublex.a20_drag_nestedscroll.drag.view

import android.content.ClipData
import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.doublex.a20_drag_nestedscroll.R

/**
 *  OnDragListener对比ViewDragHelper来说，关注的重点不同
 *
 *      OnDragListener:
 *          关注重点：「拖起 -> 放下」操作，重在内容的移动，
 *
 *          不同点：可附加拖拽数据（ClipData data  和 Object localState）
 *                 。不需要自定义view。直接使用 startDrag() / startDragAndDrop() ⼿手动开启拖拽
 *
 *          原理：拖拽的原理理是创造出⼀一个图像在屏幕的最上层，⽤用户的⼿手指拖着图像移动
 *
 *
 *
 *      ViewDragHelper:
 *          关注重点：用户拖动 ViewGroup 中的某个子 view
 *
 *          不同点：需要调用ViewDragHelper.shouldInterceptTouchEvent() 和 processTouchEvent()接管ViewGroup触摸事件反馈开启拖拽
 *
 *          原理：拖拽的原理理是实时修改被拖拽的⼦子 View 的 mLeft, mTop, mRight, mBottom 值
 */
class DragToCollectLayout : RelativeLayout {


    private val dragStarter = OnLongClickListener {
        val imageDate = ClipData.newPlainText("name", it.contentDescription)
        ViewCompat.startDragAndDrop(it, imageDate, DragShadowBuilder(it), null, 0)
    }

    private val dragListener = OnDragListener { v, event ->
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                Log.e(DragListenerGridView.TAG, "释放拖拽的view")
                if (v is LinearLayout) {
                    val textView = TextView(context).apply {
                        textSize = 16f
                        text = event.clipData.getItemAt(0).text.toString() + "-->"
                    }
                    v.addView(textView)
                }
            }
        }
        true
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<ImageView>(R.id.avatarView).setOnLongClickListener(dragStarter)
        findViewById<ImageView>(R.id.logoView).setOnLongClickListener(dragStarter)

        findViewById<LinearLayout>(R.id.collectorLayout).setOnDragListener(dragListener)
    }

}