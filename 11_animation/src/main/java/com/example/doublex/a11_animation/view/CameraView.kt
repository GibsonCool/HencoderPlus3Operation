package com.example.doublex.a11_animation.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.doublex.a11_animation.Utils

/**
 * 几何变换：
 *      canvas几何变换变化的是画布canvans,而不是图片
 */
class CameraView : View {
    val image: Bitmap
    val imageWidth = Utils.dp2px(250f)
    val imagePadding = Utils.dp2px(70f)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val camera = Camera()

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }

    var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }

    var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        image = Utils.getAvatar(resources, imageWidth.toInt())
        //避免'糊脸'效果，需要将camera在三维坐标系中的映射点往后移动，相机拍照越往后排到的画面越广一样的原理
        camera.setLocation(0f, 0f, Utils.getZFormCamera())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制上半部分
        canvas.save()
        canvas.translate(imagePadding + imageWidth / 2, imagePadding + imageWidth / 2)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-imageWidth, -imageWidth, imageWidth, 0f)
        canvas.rotate(flipRotation)
        canvas.translate(-(imagePadding + imageWidth / 2), -(imagePadding + imageWidth / 2))
        canvas.drawBitmap(image, imagePadding, imagePadding, paint)
        canvas.restore()


        //绘制下半部分
        canvas.save()
        canvas.translate(imagePadding + imageWidth / 2, imagePadding + imageWidth / 2)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-imageWidth, 0f, imageWidth, imageWidth)
        canvas.rotate(flipRotation)
        canvas.translate(-(imagePadding + imageWidth / 2), -(imagePadding + imageWidth / 2))
        canvas.drawBitmap(image, imagePadding, imagePadding, paint)
        canvas.restore()


    }

}