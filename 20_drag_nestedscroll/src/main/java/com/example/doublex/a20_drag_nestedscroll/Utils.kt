package com.example.doublex.a20_drag_nestedscroll

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

object Utils {

    fun dp2px(dp: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)


    fun getZFormCamera() = -8 * Resources.getSystem().displayMetrics.density

    val Number.dp: Int
        get() = (toInt() * Resources.getSystem().displayMetrics.density.toInt())
}