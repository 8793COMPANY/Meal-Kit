package com.corporation8793.mealkit.payment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.activity.AddreesWebActivity

import com.corporation8793.mealkit.databinding.ActivityPayMentBinding
import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import kotlinx.android.synthetic.main.activity_pay_ment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class PayMentActivity : AppCompatActivity() {

    companion object{
        lateinit var _paymentActivity : Activity
    }


    lateinit var binding : ActivityPayMentBinding
    var user_id = ""
    var address_1 = ""
    var address_2 = ""
    var post_code = ""
    var phone = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_ment)
        binding.setActionBar("주문/결제")

        _paymentActivity = this

        var product_id = intent.getStringExtra("id")
        var address = intent.getStringExtra("address")
        var shop = intent.getStringExtra("category")
        var name = intent.getStringExtra("name")
        var type = intent.getStringExtra("type")
        var price = intent.getStringExtra("price")
        var quantity = intent.getStringExtra("quantity")
        var product_amount = intent.getIntExtra("product_amount",0)
        var final_money = intent.getIntExtra("final_money",0)
        var img = intent.getStringExtra("img")

        Glide.with(this).load(img).into(binding.paymentProductImg)

        binding.paymentShopText.setText(shop)
        binding.paymentProductText.setText(name)
        binding.paymentAmountText.setText(price+"원")
        binding.paymentProductCountText.setText(quantity+"개")

        binding.paymentProductPrice.setText(product_amount.toString()+"원")
        binding.paymentFinalPrice.setText(final_money.toString()+"원")


        if (type =="1") {
            binding.paymentDeliveryInfoext.setText("배송지 주소")
            binding.paymentAddressDetail.visibility = View.VISIBLE
            binding.paymentAddressChange.visibility = View.VISIBLE
        }else {
            binding.paymentDeliveryInfoext.setText("포장 주소")
            binding.paymentAddress.setText(address)
        }

        binding.paymentActionBar.backBtn.setOnClickListener {
            finish()
        }

        binding.paymentAgreeCheckBox.setOnClickListener {
            if(binding.paymentAgreeCheckBox.isSelected) {
                binding.paymentAgreeCheckBox.isSelected = false
                binding.paymentPaymentBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.gray_dddddd)
                binding.paymentPaymentBtn.isEnabled = false
            }else {
                binding.paymentAgreeCheckBox.isSelected = true
                binding.paymentPaymentBtn.backgroundTintList = ContextCompat.getColorStateList(this,R.color.app_basic_color)
                binding.paymentPaymentBtn.isEnabled = true
            }
        }
        binding.paymentAddressChange.setOnClickListener{
            var intent = Intent(this@PayMentActivity, AddreesWebActivity::class.java)
            startActivityForResult(intent,1000)
        }


        GlobalScope.launch(Dispatchers.Default) {
            val sharedPreference = getSharedPreferences("other", 0)
            val id = sharedPreference.getString("id","test22")
            Log.e("id",id!!)
            val value = MainApplication.instance.user

            user_id = value.id
            address_1 = value.billing.address_1
            address_2 = value.billing.address_2
            post_code = value.billing.postcode
            phone = value.billing.phone


            GlobalScope.launch(Dispatchers.Main) {
                binding.paymentOrdererNameInput.setText(value.first_name)
                binding.paymentOrdererContactInput.setText(phone)
                if(type.equals("1")) {
                    binding.paymentAddress.setText(value.billing?.address_1 + ",\n")
                    binding.paymentAddressDetail.setText(value.billing?.address_2)
                }
            }
//                binding.checkText.visibility = View.VISIBLE
        }

        binding.paymentPaymentBtn.setOnClickListener {
            binding.paymentProgress.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.Default) {
//                val sharedPreference = getSharedPreferences("other", 0)
//                val id = sharedPreference.getString("id","test22")
//                val pw = sharedPreference.getString("pw","1234")
//
//                val basicAuth = Credentials.basic(id, pw)
                // 저장소 초기화
                val boardRepository = BoardRepository()

                println("====== productOrder    ======")

                println("------ makeOrder()      -----")
                // 주문 템플릿 생성
                var myOrder : Order
                if (type.equals("1")) {
                    myOrder = Order(
                            id = null,
                            date_created = null,
                            customer_id = user_id,
                            billing = Billing(MainApplication.instance.user.first_name, address_1, binding.paymentAddressDetail.text.toString(), post_code, post_code),
                            shipping = Shipping(MainApplication.instance.user.first_name, address_1, binding.paymentAddressDetail.text.toString(), post_code, post_code),
                            line_items = listOf(
                                    LineItems(name = name, product_id = product_id!!, quantity = quantity!!, total = final_money.toString())
                            ),
                            meta_data = listOf(
                                    OrderMeta(id = null, key = "store_name", value = shop!!),
                                    OrderMeta(id = null, key = "order_point", value = final_money.div(100).toString()),
                                    OrderMeta(id = null, key = "is_parcel", value = "1"),
                            )
                    )
                }else{
                    myOrder = Order(
                            id = null,
                            date_created = null,
                            customer_id = user_id,
                            billing = Billing(MainApplication.instance.user.first_name, address!!, "", post_code, post_code),
                            shipping = Shipping(MainApplication.instance.user.first_name, address!!, "", post_code, post_code),
                            line_items = listOf(
                                    LineItems(name = name, product_id = product_id!!, quantity = quantity!!, total = final_money.toString())
                            ),
                            meta_data = listOf(
                                    OrderMeta(id = null, key = "store_name", value = shop!!),
                                    OrderMeta(id = null, key = "order_point", value = final_money.div(100).toString()),
                                    OrderMeta(id = null, key = "is_parcel", value = "0"),
                            )
                    )
                }
                Log.e("PayMentActivity", "Order data: $myOrder")
                // 주문 시작
                val makeOrderResponse = boardRepository.makeOrder(myOrder)
                println("주문번호 : ${makeOrderResponse.second?.id}")
                println("상호명 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "store_name" }?.first()?.value}")
                println("주문내역 : ${makeOrderResponse.second?.line_items?.first()?.name}|${makeOrderResponse.second?.line_items?.first()?.quantity}개")
                println("결제금액 : ${makeOrderResponse.second?.line_items?.first()?.total}")
                println("적립금 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "order_point" }?.first()?.value}")

                GlobalScope.launch(Dispatchers.Main) {
                    binding.paymentProgress.visibility = View.GONE
                    var intent = Intent(applicationContext, CompleteOrdersActivity::class.java)
                    var shop_name = makeOrderResponse.second?.meta_data?.get(0)!!.value.toString()
                    var order_point = makeOrderResponse.second?.meta_data?.get(1)!!.value.toString()
                    if (type.equals("1"))
                        address = address_1+" "+address_2
                    intent.putExtra("type","payment")
                    intent.putExtra("address_type",type)
                    intent.putExtra("id",makeOrderResponse.second?.id.toString())
                    intent.putExtra("shop_name",shop_name)
                    intent.putExtra("name",makeOrderResponse.second?.line_items?.first()?.name)
                    intent.putExtra("quantity",makeOrderResponse.second?.line_items?.first()?.quantity)
                    intent.putExtra("price",makeOrderResponse.second?.line_items?.first()?.total)
                    intent.putExtra("order_point",order_point)
                    intent.putExtra("address",address)
                    startActivity(intent);


                }
//                binding.checkText.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                1000 -> {
                    val address = data?.getStringExtra("data")!!.split(",")
                    if (address != null) {
                        binding.paymentAddress.setText(address[0]+address[1])
                        post_code = address[0]
                        address_1 = address[1]
                    }
                }
            }
        }
    }
}