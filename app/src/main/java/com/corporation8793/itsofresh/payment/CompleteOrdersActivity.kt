package com.corporation8793.itsofresh.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.compose.runtime.key
import androidx.databinding.DataBindingUtil
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.activity.MainActivity
import com.corporation8793.itsofresh.databinding.ActivityCompleteOrdersBinding
import com.corporation8793.itsofresh.fragment.my.PurchaseDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CompleteOrdersActivity : AppCompatActivity() {
    lateinit var binding : ActivityCompleteOrdersBinding
    var type : String? = ""
    var payment_check = false
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
        var paid_point = intent.getStringExtra("paid_point")
        var address = intent.getStringExtra("address")
        var payment_way = intent.getStringExtra("payment_way")

        if (paid_point != "0"){
            binding.completeOrdersHoldPoint.visibility = View.VISIBLE
            binding.completeOrdersHoldPointText.visibility = View.VISIBLE
            binding.completeOrdersHoldPointText.text = paid_point+"원"
        }
        if (payment_way != "none"){
            binding.paymentCompleteText.setText("결제 확인 전입니다.")
            binding.completeOrdersPaymentWay.visibility = View.VISIBLE
            binding.completeOrdersPaymentWayText.visibility = View.VISIBLE
            binding.completeOrdersPaymentWayText.setText(payment_way)
        }

//        GlobalScope.launch(Dispatchers.Default) {
//            var data =
//                MainApplication.instance.boardRepository.listAllOrder(MainApplication.instance.user.id).second?.filter { order -> order.id.toString() == order_id }
//
//            if (data!!.get(0).payment_method == "kcp_vbank")
//                payment_check = true
//            GlobalScope.launch(Dispatchers.Main) {
//                if (payment_check) {
//                    binding.completeOrdersPaymentWay.visibility = View.VISIBLE
//                    binding.completeOrdersPaymentWayText.visibility = View.VISIBLE
//                    var where =
//                        data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_bank_name" }
//                            .first().value
//                    var account_num =
//                        data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_num" }
//                            .first().value
//                    binding.completeOrdersPaymentWayText.setText(where.toString() + " | " + account_num)
//                }
////                println(data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_bank_name" }
////                    .first().value)
////                println(data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_num" }
////                    .first().value)
////                println(data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_depositor" }
////                    .first().value)
//            }
//        }



        GlobalScope.launch(Dispatchers.Default) {
            MainApplication.instance.nonceRepository.editPoint(MainApplication.instance.board4BaRepository,MainApplication.instance.user.id,
                paid_point!!.toInt(),"-",order_id+"("+name+"|"+quantity+"개)")
        }


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