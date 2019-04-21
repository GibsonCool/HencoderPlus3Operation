package com.example.doublex.a12_material_edittext.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import com.example.doublex.a12_material_edittext.R
import com.example.doublex.a12_material_edittext.Utils

class MaterialEditText : EditText {

    val floatTextSize = Utils.dp2px(12f)
    val floatTextPadding = Utils.dp2px(8f)
    val verticalOffset = Utils.dp2px(38f)   // floatTextSize + floatTextPadding + 2 + verticalOffsetExtra
    val horizontalOffset = Utils.dp2px(5f)
    val verticalOffsetExtra = Utils.dp2px(16f)
    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val animator by lazy { ObjectAnimator.ofFloat(this, "floatingLabeFraction", 0f, 1f) }
    val backguoundPaddings = Rect()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)

    }


    var floatingLabeShow = false
    //属性动画值
    var floatingLabeFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    //可配置属性
    var userFloatingLabel = false   //是否开启悬浮label
        set(value) {
            if (field != value) {
                field = value
                updatePadding(value)
                invalidate()
            }
        }


    private fun init(context: Context, attrs: AttributeSet) {
        paint.textSize = floatTextSize
        background.getPadding(backguoundPaddings)



        for (i in 0 until attrs.attributeCount){
            Log.e("doublex","Key:${attrs.getAttributeName(i)}  value :${attrs.getAttributeValue(i)}")
        }
        /**
         * 一般自定义view中配置可选属性，可以在XML中设置，在这里读取。
         * 原理解析：attrs 就是代表XML不居中填写的各种属性 其实都在编译后的R.java文件中有对应值
         *          R.styleable.MaterialEditText 对应自定义attrs.xml中配置的属性名。在R.java文件中是一个int数组
         *          R.styleable.MaterialEditText_useFloatingLabel 表示自定义属性 userFloatingLabel 在数组中的具体索引位置
         *
         * 应用场景：游戏SDK功能开发，游戏是基于引擎开发的，在将安卓的资源打包到游戏里面的时候，一般对资源的处理是不能通过R.xx方式应用
         *          而是通过 context.resources.getIdentifier(name,className,packageName) 方式直接获取id的。为了避免很多资源找不到的问题
         *          但是这种方式对于自定义view的属性支持不好，知道了原理后，就可以直接将查找的资源id自己拼凑为数组，定义顺序来使用
         *
         * 应用场景示例：
         *
         *          id_minWidth = context.resources.getIdentifier("minWidth",xxx,xxx)
         *          id_minHeight = context.resources.getIdentifier("minHeight",xxx,xxx)
         *          id_test = context.resources.getIdentifier("test",xxx,xxx)
         *
         *          val typeArray = context.obtainStyledAttributes(attrs,intArrayOf(id_minWidth,id_minHeight,id_test))
         *
         *          id_test = typeArray.getString(2,"null")
         */


        context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText).let {
            userFloatingLabel = it.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, userFloatingLabel)
            updatePadding(userFloatingLabel)
            it.recycle()
        }


//        setPadding(paddingLeft, (paddingTop + floatTextPadding + floatTextSize).toInt(), paddingRight, paddingBottom)
        addTextChangedListener(object : TextWatcher {
            /**
             * 监听文字是否为空进行floatingLabel的动画执行
             */
            override fun afterTextChanged(s: Editable) {
                if (floatingLabeShow && s.isEmpty()) {
                    animator.reverse()
                    floatingLabeShow = !floatingLabeShow
                } else if (!floatingLabeShow && !s.isEmpty()) {
                    animator.start()
                    floatingLabeShow = !floatingLabeShow
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun updatePadding(value: Boolean) {
        if (value)
            setPadding(
                backguoundPaddings.left,
                (backguoundPaddings.top + floatTextPadding + floatTextSize).toInt(),
                backguoundPaddings.right,
                backguoundPaddings.bottom
            )
        else
            setPadding(
                backguoundPaddings.left,
                backguoundPaddings.top,
                backguoundPaddings.right,
                backguoundPaddings.bottom
            )
    }

    /**
     * edittext 每当文字输入或者删除的时候多会回调onDraw方法
     * 继承于此特性，进行定制化
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (userFloatingLabel) {
            paint.alpha = (floatingLabeFraction * 0xff).toInt()
            canvas.drawText(
                hint.toString(),
                horizontalOffset,
                verticalOffset - verticalOffsetExtra * floatingLabeFraction,
                paint
            )

        }

    }


}