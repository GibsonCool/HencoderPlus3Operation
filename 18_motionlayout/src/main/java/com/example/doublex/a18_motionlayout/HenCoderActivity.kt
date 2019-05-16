package com.example.doublex.a18_motionlayout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_hen_coder.*

class HenCoderActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hen_coder)


        showDebug.setOnClickListener {
            hencoderRoot.setDebugMode(MotionLayout.DEBUG_SHOW_PATH)
        }
    }
}