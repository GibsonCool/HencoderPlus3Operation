package com.example.doublex.a17_constraintlayout.constrainHelper

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class LinearHelper : ConstraintHelper {

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val constraintSet by lazy {
        ConstraintSet().apply { isForceId = false }
    }


    override fun updatePreLayout(container: ConstraintLayout) {
        super.updatePreLayout(container)
        constraintSet.clone(container)
        val viewIds = referencedIds

        for (i in 1 until viewIds.size) {
            val currentId = viewIds[i]
            val beforeId = viewIds[i - 1]
            constraintSet.connect(currentId, ConstraintSet.START, beforeId, ConstraintSet.START)
            constraintSet.connect(currentId, ConstraintSet.TOP, beforeId, ConstraintSet.BOTTOM)

            constraintSet.applyTo(container)
        }
    }
}