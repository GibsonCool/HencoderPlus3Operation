package com.example.doublex.a17_constraintlayout.constrainHelper

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 通过ConstraintHelper  统一设置view 的显示动画
 */
class CircularRevealHelper : ConstraintHelper {

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return

        for (referencedId in referencedIds) {
            val view = container.getViewById(referencedId)
            val radius = Math.hypot(view.width.toDouble(), view.height.toDouble()).toFloat()
            ViewAnimationUtils.createCircularReveal(view, 0, 0, 0f, radius)
                .setDuration(2000L)
                .start()
        }
    }
}