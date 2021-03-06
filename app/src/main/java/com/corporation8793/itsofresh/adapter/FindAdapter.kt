package com.corporation8793.itsofresh.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.corporation8793.itsofresh.fragment.find.FindIdFragment
import com.corporation8793.itsofresh.fragment.find.FindPwFragment

private const val NUM_TABS = 2

class FindAdapter (fragment : FragmentActivity) :
        FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FindIdFragment()
            1 -> return FindPwFragment()

        }
        return FindIdFragment()
    }

}