package com.example.doublex.a14_touch.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.doublex.a14_touch.R
import kotlinx.android.synthetic.main.fragment_touch_view.*

class TouchViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_touch_view, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        touch_view.setOnClickListener {
            it.drawableHotspotChanged(it.pivotX,it.pivotY)
            Log.e("doublex","ddddd点击了")
            Toast.makeText(context, "被点击了", Toast.LENGTH_SHORT).show()
        }
    }
}