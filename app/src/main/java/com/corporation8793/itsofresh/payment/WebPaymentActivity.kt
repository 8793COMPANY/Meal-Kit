package com.corporation8793.itsofresh.payment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WebPaymentActivity : AppCompatActivity() {
    var payment_check = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_payment_activity)


        var type = intent.getStringExtra("type")
        var payment_url = intent.getStringExtra("payment_url")!!
        var address_type = intent.getStringExtra("address_type")
        var id = intent.getStringExtra("id")
        var shop_name = intent.getStringExtra("shop_name")
        var name = intent.getStringExtra("name")
        var quantity = intent.getStringExtra("quantity")
        var price = intent.getStringExtra("price")
        var order_point = intent.getStringExtra("order_point")
        var paid_point = intent.getStringExtra("paid_point")
        var address = intent.getStringExtra("address")



        var webview = findViewById<WebView>(R.id.webView)
        var loading_bar = findViewById<RelativeLayout>(R.id.payment_progress)
        webview.getSettings().setJavaScriptEnabled(true)
        webview.webViewClient = WebViewClient()


//        webview.addJavascriptInterface(MyJavaScriptInterface(), "Android")

        webview.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.e("loading start", url.toString())

                if (url!!.equals("https://rsmpay.kcp.co.kr/pay/response_utf-8.kcp"))
                    loading_bar.visibility = View.VISIBLE

                if(url!!.contains("order-received")){
                    GlobalScope.launch(Dispatchers.Default) {
                        var data =
                            MainApplication.instance.boardRepository.listAllOrder(MainApplication.instance.user.id).second?.filter { order -> order.id.toString() == id }

                        if (data!!.get(0).payment_method == "kcp_vbank")
                            payment_check = true
                        GlobalScope.launch(Dispatchers.Main) {
                            var payment_way = "none"
                            if (payment_check) {
                                var where =
                                    data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_bank_name" }
                                        .first().value.toString()
                                var account_num =
                                    data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_num" }
                                        .first().value.toString()

                                Log.e("where",where)
                                Log.e("account_num",account_num)
                                payment_way = where + " | " + account_num
                            }

                                loading_bar.visibility = View.GONE
                                Log.e("hey","결제완료 !")
                                var intent = Intent(applicationContext, CompleteOrdersActivity::class.java)
                                intent.putExtra("type","payment")
                                intent.putExtra("payment_url",payment_url)
                                intent.putExtra("address_type",address_type)
                                intent.putExtra("id",id)
                                intent.putExtra("shop_name",shop_name)
                                intent.putExtra("name",name)
                                intent.putExtra("quantity",quantity)
                                intent.putExtra("price",price)
                                intent.putExtra("order_point",order_point)
                                intent.putExtra("paid_point",paid_point)
                                intent.putExtra("address",address)
                                intent.putExtra("payment_way",payment_way)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent);


//                println(data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_bank_name" }
//                    .first().value)
//                println(data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_num" }
//                    .first().value)
//                println(data!!.get(0).meta_data.filter { orderMeta -> orderMeta.key == "_pafw_vacc_depositor" }
//                    .first().value)
                        }
                    }

                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.e("url",url)
                webview.pageDown(true)

            }
        })

        webview.loadUrl(payment_url)

    }
}