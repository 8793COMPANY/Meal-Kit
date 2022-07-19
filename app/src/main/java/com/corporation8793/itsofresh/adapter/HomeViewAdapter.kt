package com.corporation8793.itsofresh.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corporation8793.itsofresh.fragment.home.LandFragment
import com.corporation8793.itsofresh.fragment.home.MountFragment
import com.corporation8793.itsofresh.fragment.home.OverseasFragment
import com.corporation8793.itsofresh.fragment.home.SeaFragment

private const val NUM_TABS = 4

class HomeViewAdapter (fragment : Fragment) :
        FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return LandFragment()
            1 -> return SeaFragment()
            2 -> return MountFragment()
            3 -> return OverseasFragment()
//            4 -> return MountFragment()
//            5 -> return OverseasFragment()
        }
        return LandFragment()
    }

}