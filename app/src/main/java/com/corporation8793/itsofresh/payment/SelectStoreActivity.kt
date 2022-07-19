package com.corporation8793.itsofresh.payment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.adapter.SelectStoreAdapter
import com.corporation8793.itsofresh.databinding.ActivitySelectStoreBinding
import com.corporation8793.itsofresh.dto.ShopItem
import com.corporation8793.itsofresh.esf_wp.rest.data.*
import com.corporation8793.itsofresh.esf_wp.rest.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials


class SelectStoreActivity : AppCompatActivity() {
    companion object{
        lateinit var _selectStoreActivity : Activity
    }

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
        var type = intent.getStringExtra("type")
        var name = intent.getStringExtra("name")
        var price = intent.getStringExtra("price")
        var quantity = intent.getStringExtra("quantity")
        var product_amount = intent.getIntExtra("product_amount",0)
        var final_money = intent.getIntExtra("final_money",0)
        var img = intent.getStringExtra("img")


        _selectStoreActivity = this

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
            override fun onItemClick(v: View,  pos : Int, item: ShopItem) {
                var intent = Intent(this@SelectStoreActivity, PayMentActivity::class.java)
                Log.e("address",item.address)
                intent.putExtra("id",product_id)
                intent.putExtra("category",shop)
                intent.putExtra("img",img)
                intent.putExtra("type",type)
                intent.putExtra("name",name)
                intent.putExtra("price",price)
                intent.putExtra("quantity",quantity)
                intent.putExtra("product_amount",product_amount)
                intent.putExtra("final_money",final_money)
                intent.putExtra("address",item.address)
                startActivity(intent)
            }
        })


        datas.apply {
            GlobalScope.launch(Dispatchers.Default) {
                val testId = "test22"
                val testPw = "1234"
                val basicAuth = Credentials.basic(testId, testPw)
                // 저장소 초기화
                val boardRepository = BoardRepository()

                println("====== storeList     ======")

                println("------ listAllStore() -----")
                val listAllStoreResponse = boardRepository.listAllStore()

                println("------ Filtering     -----")
                println(listAllStoreResponse.second)
                val notSaleStore = listAllStoreResponse.second?.filterNot { it.acf.disable_product.contains(DisableProduct(name!!)) }
                println("유기농두부샐러드 안파는 가게들 : ")
                if (notSaleStore != null) {
                    GlobalScope.launch(Dispatchers.Main) {
                        datas.apply {
                            notSaleStore.forEach {
                            Log.e("it",it.toString())
                            add(ShopItem(it.id,it.featured_media_src_url,it.title.rendered,it.acf.metropolitan+" "+it.acf.address))
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