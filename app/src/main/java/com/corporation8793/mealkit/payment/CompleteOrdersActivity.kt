package com.corporation8793.mealkit.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityCompleteOrdersBinding


class CompleteOrdersActivity : AppCompatActivity() {
    lateinit var binding : ActivityCompleteOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_orders)
        binding.setActionBar("주문 완료")
    }
}