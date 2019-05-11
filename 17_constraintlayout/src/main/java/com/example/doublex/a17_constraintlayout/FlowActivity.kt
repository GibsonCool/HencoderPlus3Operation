package com.example.doublex.a17_constraintlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 通过引用辅助控件的方式，简化线性布局操作也无需去写ConstraintHelper
 */
class FlowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_line)
    }
}
