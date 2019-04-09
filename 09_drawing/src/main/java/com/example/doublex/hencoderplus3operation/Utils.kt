package com.example.doublex.hencoderplus3operation

import android.content.res.Resources
import android.util.TypedValue

object Utils {

    fun dp2px(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)

}