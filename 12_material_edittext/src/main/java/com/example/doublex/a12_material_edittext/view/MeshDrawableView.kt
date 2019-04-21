package com.example.doublex.a12_material_edittext.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import com.example.doublex.a12_material_edittext.Utils

class MeshDrawableView : View {

    val drawable by lazy { MeshDrawable()}
    val drawbaleBounds = 0
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(drawbaleBounds,drawbaleBounds,width,height)
        drawable.draw(canvas)
    }
}