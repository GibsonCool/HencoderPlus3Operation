package com.example.doublex.a14_touch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.example.doublex.a14_touch.fragment.TouchViewFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    val homeTabs = ArrayList<Class<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        homeTabs.add(TouchViewFragment::class.java)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, homeTabs)
        container.adapter = mSectionsPagerAdapter
    }


    private class SectionsPagerAdapter(fm: FragmentManager, val items: List<Class<*>>) : FragmentPagerAdapter(fm) {
        private val fragments by lazy { SparseArray<Fragment>(items.size) }
        override fun getItem(position: Int): Fragment {
            if (fragments[position] == null) {
                val instance = items[position].newInstance() as Fragment
                fragments.put(position, instance)
            }

            return fragments[position]
        }

        override fun getCount(): Int {
            return items.size
        }
    }
}
