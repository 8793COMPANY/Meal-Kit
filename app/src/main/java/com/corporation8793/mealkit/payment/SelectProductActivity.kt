package com.corporation8793.mealkit.payment

import android.content.Intent
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

        var id = intent.getStringExtra("id")
        var category = intent.getStringExtra("category")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")

        binding.selectProductShopText.setText(category)
        binding.selectProductProductText.setText(name)
        binding.selectProductAmountText.setText(price+"원")

        binding.selectProductOrderBtn.setOnClickListener {
            var intent = Intent(this, PayMentActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("category",category)
            intent.putExtra("name",name)
            intent.putExtra("price",price)
            intent.putExtra("quantity",binding.selectProductCountText.text.toString())
            startActivity(intent);
        }


    }
}