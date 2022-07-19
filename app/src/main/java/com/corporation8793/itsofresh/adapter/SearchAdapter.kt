package com.corporation8793.itsofresh.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corporation8793.itsofresh.fragment.shop.RegionSearchFragment
import com.corporation8793.itsofresh.fragment.shop.SearchFragment

private const val NUM_TABS = 2

class SearchAdapter (fragment : FragmentActivity) :
        FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SearchFragment()
            1 -> return RegionSearchFragment()

        }
        return RegionSearchFragment()
    }

}