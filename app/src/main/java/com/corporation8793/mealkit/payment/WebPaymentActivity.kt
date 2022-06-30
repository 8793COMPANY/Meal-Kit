package com.corporation8793.mealkit.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.corporation8793.mealkit.R

class WebPaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_payment_activity)

        var webview = findViewById<WebView>(R.id.webView)

    }
}