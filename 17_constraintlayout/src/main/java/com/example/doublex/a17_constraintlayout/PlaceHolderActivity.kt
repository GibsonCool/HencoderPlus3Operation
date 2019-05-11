package com.example.doublex.a17_constraintlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_plach_holder.*

/**
 * 通过自定义constraintHelper
 */
class PlaceHolderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plach_holder)
    }

    fun onClick(view:View){
        TransitionManager.beginDelayedTransition(view.parent as ViewGroup)

        placeholder.setContentId(view.id)
    }
}
