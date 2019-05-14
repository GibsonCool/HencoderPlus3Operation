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