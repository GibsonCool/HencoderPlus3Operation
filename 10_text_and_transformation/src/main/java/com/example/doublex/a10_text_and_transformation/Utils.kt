package com.example.doublex.a10_text_and_transformation

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

object Utils {

    fun dp2px(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)

    fun getAvatar(resources: Resources,width:Int):Bitmap{
        val options = BitmapFactory.Options().also {
            it.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, R.drawable.avatar_doublex, it)
            it.inJustDecodeBounds = false
            it.inDensity = it.outHeight
            it.inTargetDensity = width
        }

        return BitmapFactory.decodeResource(resources, R.drawable.avatar_doublex, options)
    }

    fun getZFormCamera()= -8 * Resources.getSystem().displayMetrics.density
}