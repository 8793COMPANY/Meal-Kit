package com.corporation8793.mealkit.payment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivitySelectProductBinding


class SelectProductActivity : AppCompatActivity() {
    companion object{
        lateinit var _selectProductActivity : Activity
    }
    lateinit var binding : ActivitySelectProductBinding
    var productAmount = 0
    var finalMoney = 0
    var count = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_product)
        binding.setActionBar("상품 선택")
        _selectProductActivity = this


        var id = intent.getStringExtra("id")
        var category = intent.getStringExtra("category")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")
        var img = intent.getStringExtra("img")

        productAmount = (price!!.toInt() * count)
        finalMoney = (productAmount+3000)

        Glide.with(this).load(img).into(binding.selectProductProductImg)

        binding.selectProductShopText.setText(category)
        binding.selectProductProductText.setText(name)
        binding.selectProductAmountText.setText(price+"원")
//        binding.selectProductAmountText.setText(productAmount.toString())
        binding.selectProductProductMoneyText.setText(productAmount.toString()+"원")
        binding.selectProductFinalMoneyText.setText((productAmount+3000).toString()+"원")
        binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")

        binding.selectProductMinusBtn.setOnClickListener {
            if (count != 1) {
                count--
                productAmount = (price!!.toInt() * count)
                finalMoney = (productAmount+3000)
                binding.selectProductCountText.setText(count.toString())
                binding.selectProductProductMoneyText.setText(productAmount.toString()+"원")
                binding.selectProductFinalMoneyText.setText(finalMoney.toString()+"원")
                binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")
            }
        }

        binding.selectProductPlusBtn.setOnClickListener {
            count++
            productAmount = (price!!.toInt() * count)
            finalMoney = (productAmount+3000)
            binding.selectProductCountText.setText(count.toString())
            binding.selectProductProductMoneyText.setText(productAmount.toString()+"원")
            binding.selectProductFinalMoneyText.setText(finalMoney.toString()+"원")
            binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")
        }


        binding.selectProductOrderBtn.setOnClickListener {
            var intent = Intent(this, SelectStoreActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("category",category)
            intent.putExtra("name",name)
            intent.putExtra("price",price)
            intent.putExtra("quantity",binding.selectProductCountText.text.toString())
            intent.putExtra("product_amount",productAmount)
            intent.putExtra("final_money",finalMoney)
            startActivity(intent);
        }



//        binding.selectProductDeliveryRadioBtn.setOnClickListener {
//            Log.e("checked",binding.selectProductDeliveryRadioBtn.isChecked.toString())
//            if ()
//        }


    }
}