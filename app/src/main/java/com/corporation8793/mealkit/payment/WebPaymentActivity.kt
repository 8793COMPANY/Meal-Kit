package com.corporation8793.mealkit.payment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.corporation8793.mealkit.R

class WebPaymentActivity : AppCompatActivity() {
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
                    intent.putExtra("address",address)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent);
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