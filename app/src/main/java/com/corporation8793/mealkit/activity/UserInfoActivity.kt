package com.corporation8793.mealkit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.adapter.FindAdapter
import com.corporation8793.mealkit.adapter.ViewPagerAdapter
import com.corporation8793.mealkit.databinding.ActivityFindBinding
import com.corporation8793.mealkit.databinding.ActivityMainBinding
import com.corporation8793.mealkit.databinding.ActivityUserInfoBinding
import com.corporation8793.mealkit.fragment.my.EditUserFragment
import com.corporation8793.mealkit.fragment.my.PwCheckFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        binding.setActionBar("회원정보 수정")

        changeFragment(1)

    }

    fun changeFragment(index: Int){
        when(index){
            1 -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, PwCheckFragment())
                        .commit()
            }

            2 -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, EditUserFragment())
                        .commit()
            }
        }
    }
}