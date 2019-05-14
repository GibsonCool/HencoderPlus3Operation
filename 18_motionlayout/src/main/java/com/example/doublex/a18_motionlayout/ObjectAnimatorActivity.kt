package com.example.doublex.a18_motionlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_object_animator.*


/**
 * 简单的通过改属性参数，加上过渡动画
 */
class ObjectAnimatorActivity : AppCompatActivity() {

    private var taggle = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator)


        heart.setOnClickListener {

            TransitionManager.beginDelayedTransition(root)
            val params = it.layoutParams as FrameLayout.LayoutParams
            params.height = if (taggle) params.height / 2 else params.height * 2
            params.width = if (taggle) params.width / 2 else params.width * 2
            params.gravity = if (taggle) Gravity.START else Gravity.CENTER
            it.layoutParams = params
            taggle = !taggle
        }

    }
}