package com.corporation8793.mealkit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import kotlinx.android.synthetic.main.activity_network_check.*

class NetworkCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_check)

        findViewById<Button>(R.id.refresh_btn).setOnClickListener {
            if(MainApplication.instance.isWIFIConnected(this)){
                finish()
            }
        }
    }

    override fun onBackPressed() {

    }
}