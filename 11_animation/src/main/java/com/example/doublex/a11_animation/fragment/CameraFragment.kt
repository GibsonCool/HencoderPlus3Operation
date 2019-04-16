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
import kotlinx.android.synthetic.main.fragment_camera_view.*


class CameraFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_camera_view, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {
        bt_start.setOnClickListener {

            camera_view.apply {
                topFlip=0f
                bottomFlip=0f
                flipRotation=0f
            }

            /**
             * 多个属性同时做动画
             */
            //方式一：
            ObjectAnimator.ofFloat(camera_view, "bottomFlip", 50f).apply {
                duration = 2000
                start()
            }
//            ObjectAnimator.ofFloat(camera_view, "topFlip", -50f).apply {
//                startDelay=200
//                duration = 2000
//                start()
//            }
            ObjectAnimator.ofFloat(camera_view, "flipRotation", 270f).apply {
                startDelay=200
                duration = 2000
                start()
            }

            //方式二：使用animatorSet
//        val bottomAnimator = ObjectAnimator.ofFloat(camera_view, "bottomFlip", 50f).apply {
//            duration = 2000
//        }
//        val topAnimator = ObjectAnimator.ofFloat(camera_view, "topFlip", -50f).apply {
//            duration = 2000
//        }
//        val flipRotationAnimator = ObjectAnimator.ofFloat(camera_view, "flipRotation", 270f).apply {
//            duration = 2000
//        }
//
//
//        AnimatorSet().apply {
//            playTogether(bottomAnimator,topAnimator,flipRotationAnimator)   //同时执行
////            playSequentially(bottomAnimator,topAnimator,flipRotationAnimator) //顺序执行
//            start()
//        }

            //方式三：使用 propertyValuesHolder
//        val bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 30f)
//        val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -30f)
//        val flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)
//        ObjectAnimator.ofPropertyValuesHolder(camera_view, bottomFlipHolder, topFlipHolder, flipRotationHolder).apply {
//            duration = 2000
//            start()
//        }


        }


    }

}
