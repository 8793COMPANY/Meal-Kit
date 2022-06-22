package com.corporation8793.mealkit.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.activity.JoinActivity
import com.corporation8793.mealkit.activity.MainActivity
import com.corporation8793.mealkit.databinding.ActivityCompleteOrdersBinding


class CompleteOrdersActivity : AppCompatActivity() {
    lateinit var binding : ActivityCompleteOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_orders)
        binding.setActionBar("주문 완료")

        var order_id = intent.getStringExtra("id")
        var shop = intent.getStringExtra("shop_name")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")
        var quantity = intent.getStringExtra("quantity")
        var order_point = intent.getStringExtra("order_point")

        Log.e("order_id",order_id+"")
        binding.completeOrdersOrderNumberText.setText(order_id)
        binding.completeOrdersShopNameText.setText(shop)
        binding.completeOrdersBuyListText.setText(name+"|"+quantity+"개")
        binding.completeOrdersBuyMoneyText.setText(price+"원")
        binding.completeOrdersPointText.setText(order_point+"원")


        binding.paymentActionBar.backBtn.setOnClickListener {
            var intent = Intent(this@CompleteOrdersActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }


//

    }
}