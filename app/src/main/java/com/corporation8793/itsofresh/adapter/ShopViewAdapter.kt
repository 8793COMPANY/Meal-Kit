package com.corporation8793.itsofresh.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corporation8793.itsofresh.fragment.shop.MapFragment
import com.corporation8793.itsofresh.fragment.shop.ShopListFragment

private const val NUM_TABS = 2

class ShopViewAdapter (fragment : Fragment) :
        FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MapFragment()
            1 -> return ShopListFragment()
        }
        return ShopListFragment()
    }

}