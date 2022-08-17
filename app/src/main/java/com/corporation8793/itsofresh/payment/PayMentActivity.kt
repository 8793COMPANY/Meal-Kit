package com.corporation8793.itsofresh.payment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.activity.AddreesWebActivity

import com.corporation8793.itsofresh.databinding.ActivityPayMentBinding
import com.corporation8793.itsofresh.esf_wp.rest.data.*
import com.corporation8793.itsofresh.esf_wp.rest.repository.BoardRepository
import kotlinx.android.synthetic.main.activity_pay_ment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    var my_point = ""
    var use_point_final_price = 0
    var use_point = 0
    var final_money_check = false
    var compare_point = 0
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
        use_point_final_price = final_money

        Glide.with(this).load(img).into(binding.paymentProductImg)

        binding.paymentShopText.setText(shop)
        binding.paymentProductText.setText(name)
        binding.paymentAmountText.setText(price+"원")
        binding.paymentProductCountText.setText(quantity+"개")

        binding.paymentProductPrice.setText(product_amount.toString()+"원")
        binding.paymentFinalPrice.setText(final_money.toString()+"원")

        binding.paymentWhenBuyingPointText.setText("구매시 "+price!!.toInt().div(100).toString()+"원")



        if (type =="1") {
            binding.paymentDeliveryInfoext.setText("배송지 주소")
            binding.paymentAddressDetail.visibility = View.VISIBLE
            binding.paymentAddressChange.visibility = View.VISIBLE
        }else {
            binding.paymentDeliveryInfoext.setText("포장 주소")
            binding.paymentAddress.setText(address)
            binding.paymentDeliveryMoneyPrice.setText("0원")
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

        binding.paymentUsePointBtn.setOnClickListener {
            Log.e("click","check")
            Log.e("click",my_point)
            if(final_money > my_point.toInt()){
                binding.paymentUsepointEdit.setText(my_point)
            }else{
                binding.paymentUsepointEdit.setText(final_money.toString())
            }

        }
        var before_text = ""


        binding.paymentUsepointEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                before_text = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length != 0){
                    Log.e("hi","length not 0 in")
                    if( p0.toString().toInt() > compare_point){
                        Log.e("hi","p0 bigger than compare_point")
                        binding.paymentUsepointEdit.setText(before_text)
                        binding.paymentUsepointEdit.setSelection(binding.paymentUsepointEdit.text.length)
                        Log.e("before text",before_text)
                        if(before_text.toInt() > compare_point) {
                            use_point = 0

                        }else {
                            use_point = before_text.toInt()
                        }
                    }else{
                        binding.paymentUsePoint.setText(binding.paymentUsepointEdit.text.toString()+"원")
                        binding.paymentFinalPrice.setText((final_money - (binding.paymentUsepointEdit.text.toString().toInt())).toString()+"원")
                        use_point_final_price = final_money - (binding.paymentUsepointEdit.text.toString().toInt())
                        use_point = binding.paymentUsepointEdit.text.toString().toInt()
                    }
//                    else if (p0.toString().length >= my_point.length){
//                        Log.e("hi","p0 bigger than my_point")
//                        Log.e("final_money",final_money.toString())
//                        if (p0.toString().toInt() > my_point.toInt() ) {
//                            Log.e("unserviceable","point")
//                            binding.paymentUsepointEdit.setText(before_text)
//                            binding.paymentUsepointEdit.setSelection(binding.paymentUsepointEdit.text.length)
//
//                        }
//                    }

                }else{
                    Log.e("hi","p0 zero")
                    binding.paymentUsePoint.setText("0"+"원")
                    binding.paymentFinalPrice.setText(final_money.toString() +"원")
                    use_point = 0
                }
                Log.e("hi","use_point"+use_point)

            }

        })


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

            val validUserResponse = MainApplication.instance.nonceRepository.getValidUserInfo(user_id)
            val currentUserTotalPoint = validUserResponse.second?.meta_data?.filter {
                    metaData -> metaData.key == "point" }?.first()?.value.toString().toInt()
            println(currentUserTotalPoint)
            my_point = currentUserTotalPoint.toString()
            Log.e("my_point",my_point)


            GlobalScope.launch(Dispatchers.Main) {
                binding.paymentOrdererNameInput.setText(value.first_name)
                binding.paymentOrdererContactInput.setText(phone)
                if(my_point.toInt() <=0){
                    my_point = "0"
//                    binding.paymentServiceablePoint.text = "0"
                    binding.paymentUsepointEdit.isEnabled = false
                }

                if (my_point.toInt() < final_money){
                    compare_point = my_point.toInt()
                }else{
                    compare_point = final_money
                }

                binding.paymentServiceablePoint.text = my_point
                Log.e("my_point",my_point)
                if(type.equals("1")) {
                    binding.paymentAddress.setText(value.billing?.address_1 + ",\n")
                    binding.paymentAddressDetail.setText(value.billing?.address_2)
                }
            }
//                binding.checkText.visibility = View.VISIBLE
        }

        binding.paymentPaymentBtn.setOnClickListener {
            var intent = Intent(this, WebPaymentActivity::class.java)
            startActivity(intent)
        }

        binding.paymentPaymentBtn.setOnClickListener {
            binding.paymentProgress.visibility = View.VISIBLE
            Log.e("use_point",use_point.toString())

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
                    Log.e("price",use_point_final_price.toString())
                    var total_price = 0
                    var total_delivery_price = 3000
                    if(price.toInt() - use_point > 0)
                        total_price = price.toInt() - use_point
                    else
                        total_delivery_price = total_delivery_price + (price.toInt() - use_point)

                    Log.e("total_price",total_price.toString())
                    Log.e("total_delivery_price",total_delivery_price.toString())

                    if (use_point_final_price == 0){
                        final_money_check = true
                        myOrder = Order(
                            id = null,
                            date_created = null,
                            status = "processing",
                            customer_id = user_id,
                            billing = Billing(
                                MainApplication.instance.user.first_name,
                                address_1,
                                binding.paymentAddressDetail.text.toString(),
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            shipping = Shipping(
                                MainApplication.instance.user.first_name,
                                address_1,
                                binding.paymentAddressDetail.text.toString(),
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            line_items = listOf(
                                LineItems(
                                    name = name,
                                    product_id = product_id!!,
                                    quantity = quantity!!,
                                    total = total_price.toString()
                                )
                            ),
                            shipping_lines = listOf(
                                ShippingLines(total = total_delivery_price.toString())
                            ),
                            meta_data = listOf(
                                OrderMeta(id = null, key = "store_name", value = shop!!),
                                OrderMeta(
                                    id = null,
                                    key = "order_point",
                                    value = price!!.toInt().div(100).toString()
                                ),
                                OrderMeta(id = null, key = "is_parcel", value = "1"),
                                OrderMeta(id = null, key = "paid_point", value = use_point),
                            ),
                            payment_url = null
                        )
                    }else {
                        final_money_check = false
                        myOrder = Order(
                            id = null,
                            date_created = null,
                            customer_id = user_id,
                            billing = Billing(
                                MainApplication.instance.user.first_name,
                                address_1,
                                binding.paymentAddressDetail.text.toString(),
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            shipping = Shipping(
                                MainApplication.instance.user.first_name,
                                address_1,
                                binding.paymentAddressDetail.text.toString(),
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            line_items = listOf(
                                LineItems(
                                    name = name,
                                    product_id = product_id!!,
                                    quantity = quantity!!,
                                    total = total_price.toString()
                                )
                            ),
                            shipping_lines = listOf(
                                ShippingLines(total = total_delivery_price.toString())
                            ),
                            meta_data = listOf(
                                OrderMeta(id = null, key = "store_name", value = shop!!),
                                OrderMeta(
                                    id = null,
                                    key = "order_point",
                                    value = price!!.toInt().div(100).toString()
                                ),
                                OrderMeta(id = null, key = "is_parcel", value = "1"),
                                OrderMeta(id = null, key = "paid_point", value = use_point),
                            ),
                            payment_url = null
                        )
                    }

                }else{
                    Log.e("price",use_point_final_price.toString())
                    if (use_point_final_price == 0) {
                        final_money_check = true
                        myOrder = Order(
                            id = null,
                            date_created = null,
                            customer_id = user_id,
                            status = "processing",
                            billing = Billing(
                                MainApplication.instance.user.first_name,
                                address!!,
                                "",
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            shipping = Shipping(
                                MainApplication.instance.user.first_name,
                                address!!,
                                "",
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            line_items = listOf(
                                LineItems(
                                    name = name,
                                    product_id = product_id!!,
                                    quantity = quantity!!,
                                    total = use_point_final_price.toString()
                                )
                            ),
                            meta_data = listOf(
                                OrderMeta(id = null, key = "store_name", value = shop!!),
                                OrderMeta(
                                    id = null,
                                    key = "order_point",
                                    value = price!!.toInt().div(100).toString()
                                ),
                                OrderMeta(id = null, key = "is_parcel", value = "0"),
                                OrderMeta(id = null, key = "paid_point", value = use_point),
                            ),
                            payment_url = null
                        )
                    }else{
                        final_money_check = false
                        myOrder = Order(
                            id = null,
                            date_created = null,
                            customer_id = user_id,
                            billing = Billing(
                                MainApplication.instance.user.first_name,
                                address!!,
                                "",
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            shipping = Shipping(
                                MainApplication.instance.user.first_name,
                                address!!,
                                "",
                                post_code,
                                MainApplication.instance.user.shipping.phone
                            ),
                            line_items = listOf(
                                LineItems(
                                    name = name,
                                    product_id = product_id!!,
                                    quantity = quantity!!,
                                    total = use_point_final_price.toString()
                                )
                            ),
                            meta_data = listOf(
                                OrderMeta(id = null, key = "store_name", value = shop!!),
                                OrderMeta(
                                    id = null,
                                    key = "order_point",
                                    value = price!!.toInt().div(100).toString()
                                ),
                                OrderMeta(id = null, key = "is_parcel", value = "0"),
                                OrderMeta(id = null, key = "paid_point", value = use_point),
                            ),
                            payment_url = null
                        )
                    }
                }

                // 주문 시작
                val makeOrderResponse = boardRepository.makeOrder(myOrder)
                println("주문번호 : ${makeOrderResponse.second?.id}")
                println("상호명 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "store_name" }?.first()?.value}")
                println("주문내역 : ${makeOrderResponse.second?.line_items?.first()?.name}|${makeOrderResponse.second?.line_items?.first()?.quantity}개")
                println("결제금액 : ${makeOrderResponse.second?.line_items?.first()?.total}")
                println("적립금 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "order_point" }?.first()?.value}")
                println("적립금 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "order_point" }?.first()?.value}")

                Log.e("PayMentActivity", "Order data: $makeOrderResponse")
                GlobalScope.launch(Dispatchers.Main) {
                    binding.paymentProgress.visibility = View.GONE
                    var intent : Intent
                    if(final_money_check) {
                        intent = Intent(applicationContext, CompleteOrdersActivity::class.java)
                        intent.putExtra("payment_way","not")
                    }else
                        intent = Intent(applicationContext, WebPaymentActivity::class.java)
                    var shop_name = makeOrderResponse.second?.meta_data?.get(0)!!.value.toString()
                    var order_point = makeOrderResponse.second?.meta_data?.get(1)!!.value.toString()
                    if (type.equals("1"))
                        address = address_1+"\n"+address_2
                    intent.putExtra("type","payment")
                    intent.putExtra("payment_url",makeOrderResponse.second?.payment_url)
                    intent.putExtra("address_type",type)
                    intent.putExtra("id",makeOrderResponse.second?.id.toString())
                    intent.putExtra("shop_name",shop_name)
                    intent.putExtra("name",makeOrderResponse.second?.line_items?.first()?.name)
                    intent.putExtra("quantity",makeOrderResponse.second?.line_items?.first()?.quantity)
                    intent.putExtra("price",makeOrderResponse.second?.line_items?.first()?.total)
                    intent.putExtra("order_point",order_point)
                    intent.putExtra("paid_point",binding.paymentUsepointEdit.text.toString())
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