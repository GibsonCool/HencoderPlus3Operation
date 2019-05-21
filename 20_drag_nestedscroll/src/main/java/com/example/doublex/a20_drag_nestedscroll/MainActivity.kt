package com.example.doublex.a20_drag_nestedscroll

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities.forEach { activityInfo ->
            if (activityInfo.name == this::class.java.name)
                return@forEach

            val clazz = Class.forName(activityInfo.name)

            root_layout.addView(Button(this).apply {
                isAllCaps = false
                text = clazz.simpleName
                setOnClickListener {
                    startActivity(Intent(this@MainActivity, clazz))
                }
            })

        }
    }
}
