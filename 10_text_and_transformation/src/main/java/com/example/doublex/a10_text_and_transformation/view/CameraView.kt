package com.example.doublex.a10_text_and_transformation.view

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.doublex.a10_text_and_transformation.Utils

/**
 * 几何变换：
 *      canvas几何变换变化的是画布canvans,而不是图片
 */
class CameraView : View {
    val image: Bitmap
    val imageWidth = Utils.dp2px(300f)
    val imagePadding = Utils.dp2px(80f)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val camera = Camera()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        image = Utils.getAvatar(resources, imageWidth.toInt())
        camera.rotateX(45f)
        //避免'糊脸'效果，需要将camera在三维坐标系中的映射点往后移动，相机拍照越往后排到的画面越广一样的原理
        camera.setLocation(0f,0f,Utils.getZFormCamera())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制上半部分
        canvas.save()
        canvas.translate(imagePadding + imageWidth / 2, imagePadding + imageWidth / 2)
        canvas.rotate(-30f)
        canvas.clipRect(-imageWidth,-imageWidth,imageWidth,0f)
        canvas.rotate(30f)
        canvas.translate(-(imagePadding + imageWidth / 2), -(imagePadding + imageWidth / 2))
        canvas.drawBitmap(image, imagePadding, imagePadding, paint)
        canvas.restore()

        canvas.translate(2f,2f)

        //绘制下半部分
        canvas.save()
        canvas.translate(imagePadding + imageWidth / 2, imagePadding + imageWidth / 2)
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-imageWidth,0f,imageWidth,imageWidth)
        canvas.rotate(30f)
        canvas.translate(-(imagePadding + imageWidth / 2), -(imagePadding + imageWidth / 2))
        canvas.drawBitmap(image, imagePadding, imagePadding, paint)
        canvas.restore()


    }

}