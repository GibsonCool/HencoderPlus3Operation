package com.example.doublex.a11_animation.fragment

import android.animation.*
import android.graphics.PointF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doublex.a11_animation.ProvinceUtil
import com.example.doublex.a11_animation.R
import kotlinx.android.synthetic.main.fragmen_text_change_view.*


class TextFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragmen_text_change_view, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {

        /**
         * 通过属性动画ofObject设置文字属性动画变化
         */
        bt_start.setOnClickListener {
            text_change_view.cityText = ProvinceUtil.provinceList[0]

            ObjectAnimator.ofObject(
                text_change_view,
                "cityText",
                ProvinceUtil.ProvinceEvaluator(),
                ProvinceUtil.provinceList[ProvinceUtil.provinceList.size - 1]
            ).apply {
                duration = 3000
                start()
            }
        }


    }


}


