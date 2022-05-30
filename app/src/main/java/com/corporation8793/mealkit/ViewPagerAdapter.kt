package com.corporation8793.mealkit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corporation8793.mealkit.fragment.LandFragment
import com.corporation8793.mealkit.fragment.MountFragment
import com.corporation8793.mealkit.fragment.OverseasFragment
import com.corporation8793.mealkit.fragment.SeaFragment

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