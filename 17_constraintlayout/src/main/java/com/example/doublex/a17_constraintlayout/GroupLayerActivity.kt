package com.example.doublex.a17_constraintlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import kotlinx.android.synthetic.main.activity_group_layer.*

/**
 * Group 和 Layer  (当前使用的layer还是测试版本有点小问题)
 */
class GroupLayerActivity : AppCompatActivity() {

    var currentRotation = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_layer)

    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.bt_gone -> {
                group.visibility = Group.GONE
            }
            R.id.bt_visible -> {
                group.visibility = Group.VISIBLE
            }
            R.id.bt_rotate -> {
                currentRotation+=30
                layer.rotation = currentRotation
            }
        }
    }
}
