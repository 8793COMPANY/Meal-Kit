package com.corporation8793.itsofresh.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.databinding.ActivityUserInfoBinding
import com.corporation8793.itsofresh.fragment.my.EditUserFragment
import com.corporation8793.itsofresh.fragment.my.PwCheckFragment

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