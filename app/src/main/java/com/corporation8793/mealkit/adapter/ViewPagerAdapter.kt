package com.corporation8793.mealkit.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corporation8793.mealkit.fragment.*
import com.corporation8793.mealkit.fragment.home.LandFragment
import com.corporation8793.mealkit.fragment.home.MountFragment
import com.corporation8793.mealkit.fragment.home.OverseasFragment
import com.corporation8793.mealkit.fragment.home.SeaFragment

private const val NUM_TABS = 4

class ViewPagerAdapter (fragment : Fragment) :
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
        }
        return LandFragment()
    }

}