package com.example.doublex.a18_motionlayout

import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


/**
 * 根布局使用MotionLayout，动画场景通过应用
 *      app:layoutDescription="@xml/scene_motion_layout"
 * 来进行管理和配置，也无需在代码中进行其他操作
 */
class MotionLayoutActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)
        bindData()
    }

    private fun bindData() {
        findViewById<RatingBar>(R.id.rating_film_rating).rating = 4.5f
        findViewById<TextView>(R.id.text_film_title).text = getString(R.string.film_title)
        findViewById<TextView>(R.id.text_film_description).text = getString(R.string.film_description)
    }
}