package com.corporation8793.itsofresh.payment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.databinding.ActivitySelectProductBinding


class SelectProductActivity : AppCompatActivity() {
    companion object{
        lateinit var _selectProductActivity : Activity
    }
    lateinit var binding : ActivitySelectProductBinding
    var productAmount = 0
    var finalMoney = 0
    var count = 1
    var type = "1"
    var deliveryMoney = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_product)
        binding.setActionBar("상품 선택")
        _selectProductActivity = this

        binding.selectProductActionBar.backBtn.setOnClickListener {
            finish()
        }


        var id = intent.getStringExtra("id")
        var category = intent.getStringExtra("category")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")
        var img = intent.getStringExtra("img")

        productAmount = (price!!.toInt() * count)
        finalMoney = (productAmount+deliveryMoney)

        Glide.with(this).load(img).into(binding.selectProductProductImg)

        binding.selectProductShopText.setText(category)
        binding.selectProductProductText.setText(name)
        binding.selectProductAmountText.setText(price+"원")
//        binding.selectProductAmountText.setText(productAmount.toString())
        binding.selectProductProductMoneyText.setText(productAmount.toString()+"원")
        binding.selectProductFinalMoneyText.setText((productAmount+deliveryMoney).toString()+"원")
        binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")

        binding.selectProductMinusBtn.setOnClickListener {
            if (count != 1) {
                count--
                productAmount = (price!!.toInt() * count)
                finalMoney = (productAmount+deliveryMoney)
                binding.selectProductCountText.setText(count.toString())
                binding.selectProductProductMoneyText.setText(productAmount.toString()+"원")
                binding.selectProductFinalMoneyText.setText(finalMoney.toString()+"원")
                binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")
            }
        }

        binding.selectProductPlusBtn.setOnClickListener {
            count++
            productAmount = (price!!.toInt() * count)
            finalMoney = (productAmount+deliveryMoney)
            binding.selectProductCountText.setText(count.toString())
            binding.selectProductProductMoneyText.setText(productAmount.toString()+"원")
            binding.selectProductFinalMoneyText.setText(finalMoney.toString()+"원")
            binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")
        }


        binding.selectProductOrderBtn.setOnClickListener {
            var intent : Intent
            if (type == "0") {
                intent = Intent(this, SelectStoreActivity::class.java)

            }else{
                intent = Intent(this, PayMentActivity::class.java)
            }

            intent.putExtra("id", id)
            intent.putExtra("type", type)
            intent.putExtra("category", category)
            intent.putExtra("img", img)
            intent.putExtra("name", name)
            intent.putExtra("price", price)
            intent.putExtra("quantity", binding.selectProductCountText.text.toString())
            intent.putExtra("product_amount", productAmount)
            intent.putExtra("final_money", finalMoney)
            intent.putExtra("address", "")


            startActivity(intent);
        }


        binding.selectProductDeliveryRadioBtn.setOnClickListener {
            type = "1"
            deliveryMoney = 3000
            finalMoney = productAmount+deliveryMoney
            binding.selectProductDeliveryRadioBtn.isChecked= true
            binding.selectProductPickupRadioBtn.isChecked= false
            binding.selectProductDeliveryMoneyText.setText("3000원")
            binding.selectProductFinalMoneyText.setText(finalMoney.toString()+"원")
            binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")
        }

        binding.selectProductPickupRadioBtn.setOnClickListener {
            type = "0"
            deliveryMoney = 0
            finalMoney = productAmount+deliveryMoney
            binding.selectProductDeliveryRadioBtn.isChecked= false
            binding.selectProductPickupRadioBtn.isChecked= true
            binding.selectProductDeliveryMoneyText.setText("0원")
            binding.selectProductFinalMoneyText.setText(finalMoney.toString()+"원")
            binding.selectProductOrderBtn.setText(finalMoney.toString()+"원 주문하기")

        }


    }
}