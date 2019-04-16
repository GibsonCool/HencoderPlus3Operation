package com.example.doublex.a11_animation.fragment

import android.animation.*
import android.graphics.PointF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doublex.a11_animation.R
import com.example.doublex.a11_animation.Utils
import kotlinx.android.synthetic.main.fragmen_point_view.*


class PointFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragmen_point_view, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {

        /**
         * 通过属性动画ofObject方法不仅可以变化宽高、位移、缩放等等，还可以给变任意view的属性
         */
        val dest = PointF(Utils.dp2px(200f),Utils.dp2px(300f))

        bt_start.setOnClickListener {
            point_view.pointf= PointF(0f,0f)
            ObjectAnimator.ofObject(point_view,"pointf",MyPointFEvaluator(),dest).apply {
                duration=2000
                start()
            }
        }



    }

    //定义属性计算器
    class MyPointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val x = startValue.x + (endValue.x - startValue.x) * fraction
            val y = startValue.y + (endValue.y - startValue.y) * fraction
            return PointF(x, y)
        }
    }
}


