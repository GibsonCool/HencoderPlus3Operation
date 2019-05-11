package com.example.doublex.a17_constraintlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/**
 * 通过constraintSet.clone可以从其他布局中获取约束集应用到当前布局中
 * 由于减少了布局嵌套 ，还可以通过加上TransitionManager来添加过渡动画
 */
class ConstraintSetActivity : AppCompatActivity() {
    private val constraintLayout by lazy { findViewById<ConstraintLayout>(R.id.constraintLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constranit_start)
    }

    fun onClick(view: View) {
        //加上过渡动画
        TransitionManager.beginDelayedTransition(constraintLayout)

        val constraintSet = ConstraintSet().apply {
            //防止布局中有 无id的 控件，
            isForceId = false
            clone(
                this@ConstraintSetActivity,
                when (view.id) {
                    R.id.twitter -> R.layout.activity_constranit_end
                    R.id.wechat -> R.layout.activity_constranit_center
                    R.id.weibo->R.layout.activity_constranit_circular
                    R.id.qzone->R.layout.activity_constranit_center2
                    else -> R.layout.activity_constranit_start

                }
            )
        }

        constraintSet.applyTo(constraintLayout)
    }
}
