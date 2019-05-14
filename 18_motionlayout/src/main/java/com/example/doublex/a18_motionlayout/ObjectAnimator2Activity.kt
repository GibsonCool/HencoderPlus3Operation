package com.example.doublex.a18_motionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_object_animator.*
import kotlinx.android.synthetic.main.activity_object_animator2.*


/**
 *
 */
class ObjectAnimator2Activity : AppCompatActivity() {

    private var taggle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator2)

        button_2.setOnClickListener {

            /**
             *  这种方式只会改变当前view的属性大小
             *  在布局中如果有宽高大小变化不会随之变化
             */
//            val value = if (taggle) 2f else 1f
//            it.animate()
//                .scaleX(value)
//                .scaleY(value)
//                .start()

            /**
             * 使用过渡动画在view改变的同时，宽高涉及变动也会有变化、
             *
             * 过渡动画大致原理流程：
             *
             *  1、过渡在两个场景之间过渡，开始场景和结束场景。记录两个场景的控件的各种参数
             *
             *  2、根据场景参数，创建出动画对象
             *
             *  3、播放动画
             *
             *
             *
             *
             * 代码流程：
             *
             *  1 beginDelayedTransition()->sceneChangeSetup()->Transition.captureValues()
             *  2 根据captureValues(**,true） 方法中传入的boolean值判断是记录 开始场景
             *  3 设置给ViewGroup设置 OnPreDrawListener 和 OnAttachStateChangeListener 监听对象MultiListener
             *  4 在MultiListener监听回调中，当布局发生变化首先调用 captureValues(**,false） 记录结束场景
             *  5 然后根据开始和结束场景参数创建动画，并调用playTransition（）播放动画
             */
            TransitionManager.beginDelayedTransition(it.parent as ViewGroup)
            val params = it.layoutParams as LinearLayout.LayoutParams
            params.height = if (taggle) params.height / 2 else params.height * 2
            params.width = if (taggle) params.width / 2 else params.width * 2
            it.layoutParams = params

            taggle = !taggle
        }


    }
}