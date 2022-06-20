package com.corporation8793.mealkit.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivitySelectProductBinding


class SelectProductActivity : AppCompatActivity() {
    lateinit var binding : ActivitySelectProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_product)
        binding.setActionBar("상품 선택")
    }
}