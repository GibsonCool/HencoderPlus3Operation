package com.example.doublex.hencoderplus3operation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.example.doublex.hencoderplus3operation.data.HomeTab
import com.example.doublex.hencoderplus3operation.fragment.FirstFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    val homeTabs = ArrayList<HomeTab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        homeTabs.add(HomeTab(FirstFragment::class.java))
        homeTabs.add(HomeTab(FirstFragment::class.java))
        homeTabs.add(HomeTab(FirstFragment::class.java))

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,homeTabs)
        container.adapter = mSectionsPagerAdapter
    }




    private class SectionsPagerAdapter(fm: FragmentManager, val items: List<HomeTab>) : FragmentPagerAdapter(fm) {
        private val fragments by lazy { SparseArray<Fragment>(items.size) }
        override fun getItem(position: Int): Fragment {
            if(fragments[position]==null){
                val instance = items[position].fragmentClass.newInstance() as Fragment
                fragments.put(position,instance)
            }

            return fragments[position]
        }

        override fun getCount(): Int {
            return items.size
        }
    }


}
