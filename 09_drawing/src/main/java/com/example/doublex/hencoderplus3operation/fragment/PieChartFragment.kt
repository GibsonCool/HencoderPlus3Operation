package com.example.doublex.hencoderplus3operation.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doublex.hencoderplus3operation.R
import kotlinx.android.synthetic.main.fragment_first.view.*

class PieChartFragment:Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_pie_chart, container, false)
        return rootView
    }
}