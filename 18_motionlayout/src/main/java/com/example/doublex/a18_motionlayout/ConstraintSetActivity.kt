package com.example.doublex.a18_motionlayout

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_go.*

/**
 *
 */
class ConstraintSetActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_start)

        bindData()
    }

    private fun bindData() {
        findViewById<ImageView>(R.id.image_film_cover).setOnClickListener(this)
        findViewById<RatingBar>(R.id.rating_film_rating).rating = 4.5f
        findViewById<TextView>(R.id.text_film_title).text = getString(R.string.film_title)
        findViewById<TextView>(R.id.text_film_description).text = getString(R.string.film_description)
    }

    private var taggle = false

    /**
     * 借助于ConstraintLayout的ConstraintSet 和 TransitionManager 可以做到将场景变化提取到不通xml维护
     * 并且按需切换还不用重新绑定数据。实现了TransitionManger的 go() 和 beginDelayedTransition(）的优点结合
     *
     *      「通过ConstraintSet吸取不同布局中的约束参数信息，在用TransitionManger来完成过渡动画」
     *
     *
     *  思考：这种结合许多优点的动画方式还有什么不足？
     *
     *      1、动画只有开始和结束两个之间切换，不可控制完成百分比
     *      2、没有触摸反馈，不可通过自己滑动执行
     *      3、多个不同xml布局中有许多重复冗余的控件，只是为了不同场景而生的布局文件并不会用于实际应用只是为了提供给ConstraintSet吸取约束
     *
     *  基于以上问题google 在ConstraintLayout和ConstraintSet的基础上提供出了「MotionLayout」
     *      1、继承ConstraintLayout布局摆放约束信息不变
     *      2、布局只需要一个layout
     *      3、将场景变化，动画信息抽离「xml」资源文件下，用xml方式来配置
     *      4、支持动画可控，触摸反馈
     *
     *  具体实现查看
     *  @see MotionLayoutActivity
     */
    override fun onClick(v: View?) {
        TransitionManager.beginDelayedTransition(root)
        val constraintSet = ConstraintSet().apply {
            clone(
                this@ConstraintSetActivity,
                if (taggle) R.layout.activity_constraint_end else R.layout.activity_constraint_start
            )
        }
        constraintSet.applyTo(root)
        taggle = !taggle
    }

}