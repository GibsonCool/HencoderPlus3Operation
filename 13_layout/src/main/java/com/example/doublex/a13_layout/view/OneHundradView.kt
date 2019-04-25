package com.example.doublex.a13_layout.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View

class OneHundradView : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    /**
     * 方式一：不理会XML布局中开发者设置的宽高，固定死测量的宽高值为100(可能遇到某些ViewGroup会放弃子View的测量结果，这种方式也就没法固定匡高)
     */
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        Log.e("doublex","width:$widthMeasureSpec  height:$heightMeasureSpec")
//        setMeasuredDimension(100,100)
//    }


    /**
     * 方式二：强制布局时候坐标位置为固定宽高，忽略XML设置的值，这种方式不依赖测量结果 ，
     *        父ViewGroup也没法干涉（一般不要这么操作，强行破坏了整个view的流程了）,
     *        而一个父View中的如果涉及剩余可用空间的计算就对不上，会导致这个view与父ViewGroup中的其他view布局重叠或者错乱
     */
    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, l+100, t+100 )
    }

}