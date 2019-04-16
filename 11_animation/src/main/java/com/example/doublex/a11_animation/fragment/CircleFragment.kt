package com.example.doublex.a11_animation.fragment

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doublex.a11_animation.R
import com.example.doublex.a11_animation.Utils
import kotlinx.android.synthetic.main.fragment_circle_view.*


class CircleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_circle_view, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {

        bt_start.setOnClickListener {

            circle_view.radius=0f
            circle_view.translationX=0f

            ObjectAnimator.ofFloat(circle_view, "radius", Utils.dp2px(100f)).apply {
                startDelay = 1000
                duration = 2000
                start()
            }


            /**
             * 使用关键帧方式可以具体控制定义动画执行进度和属性值
             */
            val distance = Utils.dp2px(200f)
            val keyframe1 = Keyframe.ofFloat(0f, 0f)
            val keyframe2 = Keyframe.ofFloat(0.1f, 1.5f * distance)
            val keyframe3 = Keyframe.ofFloat(0.9f, 0.6f * distance)
            val keyframe4 = Keyframe.ofFloat(1f, distance)
            val holder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4)
            val animator = ObjectAnimator.ofPropertyValuesHolder(circle_view, holder)
            animator.startDelay = 3000
            animator.duration = 2000
            animator.start()
        }


    }

}

