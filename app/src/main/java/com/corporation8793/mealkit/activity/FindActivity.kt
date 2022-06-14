package com.corporation8793.mealkit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.adapter.FindAdapter
import com.corporation8793.mealkit.adapter.ViewPagerAdapter
import com.corporation8793.mealkit.databinding.ActivityFindBinding
import com.corporation8793.mealkit.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FindActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find)
        binding.setActionBar("아이디 찾기")



        binding.viewpager.adapter = FindAdapter(this)
        binding.viewpager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        binding.viewpager.setCurrentItem(1,false)
//
        val tabName = arrayOf<String>("아이디 찾기","비밀번호 찾기")


        TabLayoutMediator(binding.kindOfSearch, binding.viewpager) { tab, position ->
            tab.text = tabName[position].toString()
        }.attach()


    }
}