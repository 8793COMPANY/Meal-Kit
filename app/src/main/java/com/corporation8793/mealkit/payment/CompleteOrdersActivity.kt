package com.corporation8793.mealkit.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.activity.JoinActivity
import com.corporation8793.mealkit.activity.MainActivity
import com.corporation8793.mealkit.databinding.ActivityCompleteOrdersBinding
import com.corporation8793.mealkit.fragment.BestFragmentDirections
import com.corporation8793.mealkit.fragment.my.PurchaseDetailsActivity


class CompleteOrdersActivity : AppCompatActivity() {
    lateinit var binding : ActivityCompleteOrdersBinding
    var type : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_orders)
        binding.setActionBar("주문 완료")

        type = intent.getStringExtra("type")
        var order_id = intent.getStringExtra("id")
        var address_type = intent.getStringExtra("address_type")
        var shop = intent.getStringExtra("shop_name")
//        var store_address = intent.getStringExtra("store_address")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")
        var quantity = intent.getStringExtra("quantity")
        var order_point = intent.getStringExtra("order_point")
        var address = intent.getStringExtra("address")

        if(type.equals("check"))
            binding.completeOrdersOrderListBtn.visibility = View.GONE

        if(address_type.equals("1")) {
            binding.completeOrdersDeliveryAddressText.setText(address)
            binding.completeOrdersShopAddress.setText("주소")
        }else
            binding.completeOrdersDeliveryAddressText.setText(address)


        Log.e("order_id",order_id+"")
        Log.e("quantity",quantity+"")
        binding.completeOrdersOrderNumberText.setText(order_id)
        binding.completeOrdersShopNameText.setText(shop)
        binding.completeOrdersBuyListText.setText(name+"|"+quantity+"개")
        binding.completeOrdersBuyMoneyText.setText(price+"원")
        binding.completeOrdersPointText.setText(order_point+"원")



        binding.paymentActionBar.backBtn.setOnClickListener {
            if (type.equals("check"))
                finish()
            else{
                var intent = Intent(this@CompleteOrdersActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }

        binding.completeOrdersOrderListBtn.setOnClickListener {
            var intent = Intent(this@CompleteOrdersActivity, PurchaseDetailsActivity::class.java)
            intent.putExtra("type","purchase")
            startActivity(intent)
        }


//

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (type.equals("check"))
            finish()
        else{
            var intent = Intent(this@CompleteOrdersActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}