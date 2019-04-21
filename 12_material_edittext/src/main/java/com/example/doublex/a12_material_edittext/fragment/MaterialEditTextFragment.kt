package com.example.doublex.a12_material_edittext.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doublex.a12_material_edittext.R
import kotlinx.android.synthetic.main.fragmen_material_edittext.*


class MaterialEditTextFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragmen_material_edittext, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_change.setOnClickListener {
            et_material.userFloatingLabel = !et_material.userFloatingLabel
        }
    }


}


