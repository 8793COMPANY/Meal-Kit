package com.corporation8793.mealkit.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.activity.JoinActivity

import com.corporation8793.mealkit.databinding.ActivityPayMentBinding
import com.corporation8793.mealkit.esf_wp.rest.api_interface.nonce.BoardService
import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.SignUpBody
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import kotlinx.android.synthetic.main.activity_pay_ment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class PayMentActivity : AppCompatActivity() {

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

        var product_id = intent.getStringExtra("id")
        var shop = intent.getStringExtra("category")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")
        var quantity = intent.getStringExtra("quantity")
        var product_amount = intent.getIntExtra("product_amount",0)
        var final_money = intent.getIntExtra("final_money",0)


        binding.paymentShopText.setText(shop)
        binding.paymentProductText.setText(name)
        binding.paymentAmountText.setText(price+"원")
        binding.paymentProductCountText.setText(quantity+"개")

        binding.paymentProductPrice.setText(product_amount.toString()+"원")
        binding.paymentFinalPrice.setText(final_money.toString()+"원")

        GlobalScope.launch(Dispatchers.Default) {
            val sharedPreference = getSharedPreferences("other", 0)
            val editor = sharedPreference.getString("id","test22")
            val value = NonceRepository().checkUsername(editor!!)

            println(value.first)
            println(value.second?.get(0))
            user_id = value.second?.get(0)!!.id
            address_1 = value.second?.get(0)!!.billing.address_1
            address_2 = value.second?.get(0)!!.billing.address_2
            post_code = value.second?.get(0)!!.billing.postcode
            phone = value.second?.get(0)!!.billing.phone


            GlobalScope.launch(Dispatchers.Main) {
                binding.paymentOrdererNameInput.setText(value.second?.get(0)?.first_name)
                binding.paymentOrdererContactInput.setText(phone)
                binding.address.setText(value.second?.get(0)?.billing?.address_1+",\n"+value.second?.get(0)?.billing?.address_2)

            }
//                binding.checkText.visibility = View.VISIBLE
        }

        binding.paymentPaymentBtn.setOnClickListener {

            GlobalScope.launch(Dispatchers.Default) {
                val sharedPreference = getSharedPreferences("other", 0)
                val id = sharedPreference.getString("id","test22")
                val pw = sharedPreference.getString("pw","1234")

                val basicAuth = Credentials.basic(id, pw)
                // 저장소 초기화
                val boardRepository = BoardRepository(basicAuth)

                println("====== productOrder    ======")

                println("------ makeOrder()      -----")
                // 주문 템플릿 생성
                val myOrder = Order(
                        id = null,
                        date_created = null,
                        customer_id = user_id,
                        billing = Billing(address_1, address_2, post_code, post_code),
                        shipping = Shipping(address_1, address_2, post_code, post_code),
                        line_items = listOf(
                                LineItems(name = name, product_id = product_id!!, quantity = quantity!!, total = final_money.toString())
                        ),
                        meta_data = listOf(
                                OrderMeta(id = null, key = "store_name", value = shop!!),
                                OrderMeta(id = null, key = "order_point", value = "189"),
                        )
                )
                // 주문 시작
                val makeOrderResponse = boardRepository.makeOrder(myOrder)
                println("주문번호 : ${makeOrderResponse.second?.id}")
                println("상호명 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "store_name" }?.first()?.value}")
                println("주문내역 : ${makeOrderResponse.second?.line_items?.first()?.name}|${makeOrderResponse.second?.line_items?.first()?.quantity}개")
                println("결제금액 : ${makeOrderResponse.second?.line_items?.first()?.total}")
                println("적립금 : ${makeOrderResponse.second?.meta_data?.filter { orderMeta -> orderMeta.key == "order_point" }?.first()?.value}")

                GlobalScope.launch(Dispatchers.Main) {
                    var intent = Intent(applicationContext, CompleteOrdersActivity::class.java)
                    var shop_name = makeOrderResponse.second?.meta_data?.get(0)!!.value.toString()
                    var order_point = makeOrderResponse.second?.meta_data?.get(1)!!.value.toString()
                    intent.putExtra("id",makeOrderResponse.second?.id)
                    intent.putExtra("shop_name",shop_name)
                    intent.putExtra("name",makeOrderResponse.second?.line_items?.first()?.name)
                    intent.putExtra("quantity",makeOrderResponse.second?.line_items?.first()?.quantity)
                    intent.putExtra("price",makeOrderResponse.second?.line_items?.first()?.total)
                    intent.putExtra("order_point",order_point)
                    startActivity(intent);

                }
//                binding.checkText.visibility = View.VISIBLE
            }
        }
    }
}