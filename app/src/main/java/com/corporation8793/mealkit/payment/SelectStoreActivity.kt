package com.corporation8793.mealkit.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.adapter.SelectStoreAdapter
import com.corporation8793.mealkit.databinding.ActivitySelectStoreBinding
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.dto.ShopItem
import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.*
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.SignUpBody
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials


class SelectStoreActivity : AppCompatActivity() {
    lateinit var binding : ActivitySelectStoreBinding
    var productAmount = 0
    var finalMoney = 0
    var count = 1
    val datas = mutableListOf<ShopItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_store)
        binding.setActionBar("매장 선택")


        var product_id = intent.getStringExtra("id")
        var shop = intent.getStringExtra("category")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")
        var quantity = intent.getStringExtra("quantity")
        var product_amount = intent.getIntExtra("product_amount",0)
        var final_money = intent.getIntExtra("final_money",0)

        binding.selectProductActionBar.backBtn.setOnClickListener {
            finish()
        }


        val display : DisplayMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()

        val shopAdapter = SelectStoreAdapter(this,this, height)

        binding.shopList.adapter = shopAdapter
        val lm = LinearLayoutManager(this)
        binding.shopList.layoutManager = lm

        binding.shopList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        shopAdapter.setOnItemClickListener(object : SelectStoreAdapter.OnItemClickListener{
            override fun onItemClick(v: View,  pos : Int) {
                var intent = Intent(this@SelectStoreActivity, PayMentActivity::class.java)
                intent.putExtra("id",product_id)
                intent.putExtra("category",shop)
                intent.putExtra("name",name)
                intent.putExtra("price",price)
                intent.putExtra("quantity",quantity)
                intent.putExtra("product_amount",product_amount)
                intent.putExtra("final_money",final_money)
                startActivity(intent)
            }
        })


        datas.apply {
            GlobalScope.launch(Dispatchers.Default) {
                val testId = "test22"
                val testPw = "1234"
                val basicAuth = Credentials.basic(testId, testPw)
                // 저장소 초기화
                val boardRepository = BoardRepository(basicAuth)

                println("====== storeList     ======")

                println("------ listAllStore() -----")
                val listAllStoreResponse = boardRepository.listAllStore()

                println("------ Filtering     -----")
                println(listAllStoreResponse.second)
                val notSaleStore = listAllStoreResponse.second?.filter { !it.acf.disable_product.contains(DisableProduct("바질시금치스프")) }
                println("유기농두부샐러드 안파는 가게들 : ")
                if (notSaleStore != null) {
                    GlobalScope.launch(Dispatchers.Main) {
                        datas.apply {
                        listAllStoreResponse.second!!.forEach {
                            add(ShopItem(it.featured_media_src_url,it.title.rendered,"광주 동구 동계천로 150"))
                        }
                            shopAdapter.datas = datas
                            shopAdapter.notifyDataSetChanged()
                        }
                    }
                }





            }
        }





//        binding.selectProductDeliveryRadioBtn.setOnClickListener {
//            Log.e("checked",binding.selectProductDeliveryRadioBtn.isChecked.toString())
//            if ()
//        }


    }
}