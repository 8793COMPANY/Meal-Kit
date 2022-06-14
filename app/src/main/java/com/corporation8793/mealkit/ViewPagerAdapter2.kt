package com.corporation8793.mealkit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corporation8793.mealkit.fragment.*

private const val NUM_TABS = 2

class ViewPagerAdapter2 (fragment : Fragment) :
        FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MapFragment()
            1 -> return ShopListFragment()
        }
        return MapFragment()
    }

}