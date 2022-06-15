package com.corporation8793.mealkit.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R

import com.corporation8793.mealkit.databinding.ActivityPayMentBinding

class PayMentActivity : AppCompatActivity() {

    lateinit var binding : ActivityPayMentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_ment)
        binding.setActionBar("주문/결제")
    }
}