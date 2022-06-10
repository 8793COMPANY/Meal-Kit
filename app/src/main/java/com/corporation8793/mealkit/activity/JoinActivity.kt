package com.corporation8793.mealkit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.title = "회원가입"
    }
}