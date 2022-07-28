package com.corporation8793.itsofresh.fragment.my

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.activity.MainActivity
import com.corporation8793.itsofresh.adapter.PurchaseAdapter
import com.corporation8793.itsofresh.databinding.ActivityPurchaseDetailsBinding
import com.corporation8793.itsofresh.decoration.KitDecoration
import com.corporation8793.itsofresh.dto.PurchaseItem
import com.corporation8793.itsofresh.esf_wp.rest.RestClient
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PurchaseDetailsActivity : AppCompatActivity() {
    lateinit var binding : ActivityPurchaseDetailsBinding
    var datas = mutableListOf<PurchaseItem>()
    var type : String? = ""
    val kit_status = mapOf("on-hold" to "결제 확인 중","pending" to "결제 확인 전", "processing" to "처리중",
            "shipping" to "결제 확인 중", "shipped" to "처리중",
            "cancelled" to "취소됨", "cancel-request" to "주문취소요청",
            "completed" to "완료됨")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase_details)
        binding.setActionBar("비밀번호 변경")

        type = intent.getStringExtra("type")

        val display : DisplayMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 3).toInt()


//        val purchase_list = view.findViewById<RecyclerView>(R.id.purchase_list)
//        val purchase_progress = view.findViewById<RelativeLayout>(R.id.purchase_progress)

        val adapter = PurchaseAdapter(this,this,height)

        binding.backBtn.setOnClickListener {
            if (type.equals("purchase")) {
                var intent = Intent(this@PurchaseDetailsActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }else
                finish()

        }

        binding.purchaseList.adapter = adapter

        val lm = LinearLayoutManager(this)
        binding.purchaseList.layoutManager = lm
        var pos =0


        GlobalScope.launch(Dispatchers.Default) {
            //      var user_id = RestClient.nonceService.checkUsername(MainApplication.instance.user.).execute().body()!!.get(0).id
            val item = RestClient.boardService.listAllOrder(MainApplication.instance.user.id).execute().body()!!
            Log.e("listAllOrder", "PDA isEmpty : ${item.isEmpty()}")
            if (item.isNotEmpty()) {
                datas.apply {
                    item.forEach {
                        var img_pos =0
                        Log.e("item",it.toString())
                        var address_type = "0"
                        var status = it.status.toString()
                        Log.e("meta_data","start")
                        if (it.meta_data.filter { orderMeta ->  orderMeta.key == "is_parcel"}.size != 0)
                            address_type = it.meta_data.filter { orderMeta ->  orderMeta.key == "is_parcel"}.get(0).value.toString()
//                        Log.e("in!",it.meta_data.filter { orderMeta ->  orderMeta.key == "is_parcel"}.get(0).value.toString())
                        var paid_point = it.meta_data.filter { orderMeta ->  orderMeta.key == "paid_point"}.first().value.toString()
                        Log.e("paid_point",paid_point)
                        Log.e("meta_data","end")
                        if (it.status.equals("failed"))
                            status = "조회 실패"
                        else
                            status = kit_status[it.status]!!

                        Log.e("img ","start")

                        var payment_way = "none"
                        if(it.payment_method == "kcp_vbank") {
                            var where =
                                it.meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_bank_name" }
                                    .first().value.toString()
                            var account_num =
                                it.meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_num" }
                                    .first().value.toString()
                            payment_way = where +" | "+account_num
                        }


                        Log.e("img","end")
                        add(PurchaseItem(pos,address_type,"0",it.id.toString(),it.line_items.get(0).product_id,it.date_created!!.replace("T"," "),
                            it.meta_data.get(0).value.toString() ,it.line_items.get(0).name.toString(),
                            it.line_items.get(0).total,it.line_items.get(0).quantity,status,it.billing.address_1+"\n"+it.billing.address_2
                            ,paid_point,payment_way))

                        img_pos = pos
                        GlobalScope.launch(Dispatchers.Default) {
                            var img = MainApplication.instance.boardRepository.retrieveOneProduct(it.line_items.get(0).product_id).second!!.images.get(0).src
                            if (item.size > img_pos)
                                datas.get(img_pos).img= img
//                        datas.get(pos).img= img
                            GlobalScope.launch(Dispatchers.Main) {
                                adapter.notifyDataSetChanged()
                            }
                        }

                        pos++

                        GlobalScope.launch(Dispatchers.Main) {
                            adapter.datas = datas
                            adapter.notifyDataSetChanged()
                            binding.purchaseProgress.visibility = View.GONE
                        }
//                        println("상품 카테고리 : ${pr.categories.first().name}")
//                        println("상품명 : ${pr.name} | (주문 id : ${pr.id})")
//                        println("별점 (5.00) : ${pr.average_rating}")
//                        println("상품 이미지 URL : ${pr.images.first().src}")
//                        println("상품 세일 기간 : ${pr.date_on_sale_from} ~ ${pr.date_on_sale_to}")
//                        println("상품가격 : ${pr.price}원")
//                        println("재고정보 : ${pr.stock_quantity} / ${pr.acf.total_stock}개")
//                        println("---------------")

                    }

//                GlobalScope.launch(Dispatchers.Main) {
//                    adapter.datas = datas
//                    adapter.notifyDataSetChanged()
//                    binding.purchaseProgress.visibility = View.GONE
//                }

                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    adapter.datas = datas
                    adapter.notifyDataSetChanged()
                    binding.purchaseProgress.visibility = View.GONE
                }
            }
//                binding.checkText.visibility = View.VISIBLE
        }

        var count = 0


//        GlobalScope.launch(Dispatchers.Default) {
//            var user_id = RestClient.nonceService.checkUsername(id!!).execute().body()!!.get(0).id
//            Log.e("in","start")
//            val item = MainApplication.instance.boardRepository.listAllOrder(user_id)
//            Log.e("in","end")
//            val seconde = item.second!!
//            Log.e("item",item.first)
//                datas.apply {
//                    seconde.forEach {
//                        Log.e("item",it.toString())
////                        Log.e("third",third.get(count).first)
//
//
//                        var img = MainApplication.instance.boardRepository.retrieveOneProduct(it.line_items.get(0).product_id).second!!.images.get(0).src
//
//                        add(PurchaseItem(img,it.id.toString(),it.line_items.get(0).product_id,it.date_created!!.replace("T"," "),
//                               it.meta_data.get(0).value.toString() ,it.line_items.get(0).name.toString(),
//                                it.line_items.get(0).total,it.line_items.get(0).quantity,it.billing.address_1+" "+it.billing.address_2))
////                        println("상품 카테고리 : ${pr.categories.first().name}")
////                        println("상품명 : ${pr.name} | (주문 id : ${pr.id})")
////                        println("별점 (5.00) : ${pr.average_rating}")
////                        println("상품 이미지 URL : ${pr.images.first().src}")
////                        println("상품 세일 기간 : ${pr.date_on_sale_from} ~ ${pr.date_on_sale_to}")
////                        println("상품가격 : ${pr.price}원")
////                        println("재고정보 : ${pr.stock_quantity} / ${pr.acf.total_stock}개")
////                        println("---------------")
//                        count++
//                    }
//                    GlobalScope.launch(Dispatchers.Main) {
//                    adapter.datas = datas
//                    adapter.notifyDataSetChanged()
//                        purchase_progress.visibility = View.GONE
//                }
//            }
////                binding.checkText.visibility = View.VISIBLE
//        }
//>>>>>>> 3bd90988ffe4469139fe9f44944597b1c9654a6f



        var divider = KitDecoration(20)
        binding.purchaseList.addItemDecoration(divider)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (type.equals("purchase")) {
            var intent = Intent(this@PurchaseDetailsActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }else
            finish()
    }
}