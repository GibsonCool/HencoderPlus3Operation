package com.example.doublex.a18_motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleActivity : AppCompatActivity() {

    var toggle = false
    var layoutToggle = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        bt_show_path.setOnClickListener {
            toggle = !toggle
            motionRoot.setDebugMode(if (toggle) 2 else 1)
        }

        btToggleDescription.setOnClickListener {
            layoutToggle = !layoutToggle
            motionRoot.loadLayoutDescription(if (layoutToggle) R.xml.simple2 else R.xml.simple)
        }
    }
}