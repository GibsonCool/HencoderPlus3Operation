package com.example.doublex.a17_constraintlayout

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_circular_position.*

/**
 * 约束布局圆形定位示例
 */
class CircularPositioning : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_position)

        val earthAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 10000L
            repeatCount = INFINITE
            interpolator = LinearInterpolator()
        }

        val moonAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 5000L
            repeatCount = INFINITE
            interpolator = LinearInterpolator()
        }

        earthAnimator.addUpdateListener {
            val params = earth.layoutParams as ConstraintLayout.LayoutParams
            params.circleAngle = 45 + it.animatedFraction * 360
            earth.requestLayout()
        }

        moonAnimator.addUpdateListener {
            val params = moon.layoutParams as ConstraintLayout.LayoutParams
            params.circleAngle = 270 + it.animatedFraction * 360
            moon.requestLayout()
        }

        sun.setOnClickListener {
            earthAnimator.start()
            moonAnimator.start()
        }
    }
}
