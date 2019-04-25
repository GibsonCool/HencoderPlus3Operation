package com.example.doublex.a13_layout

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.doublex.a13_layout.fragment.CircleViewFragment
import com.example.doublex.a13_layout.fragment.OneHundredFragment
import com.example.doublex.a13_layout.fragment.SquareImageFragment
import com.example.doublex.a13_layout.fragment.TagLayoutFragment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_square.view.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    val homeTabs = ArrayList<Class<*>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        homeTabs.add(TagLayoutFragment::class.java)
        homeTabs.add(CircleViewFragment::class.java)
        homeTabs.add(SquareImageFragment::class.java)
        homeTabs.add(OneHundredFragment::class.java)

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
