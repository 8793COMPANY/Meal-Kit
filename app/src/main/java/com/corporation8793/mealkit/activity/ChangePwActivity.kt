package com.corporation8793.mealkit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityChangePwBinding

class ChangePwActivity : AppCompatActivity() {
    lateinit var binding : ActivityChangePwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_pw)
        binding.setActionBar("비밀번호 변경")

        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }

        binding.findIdBox.setText(intent.getStringExtra("email"))

    }
}