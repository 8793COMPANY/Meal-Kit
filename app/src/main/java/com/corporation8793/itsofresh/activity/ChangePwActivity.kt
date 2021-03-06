package com.corporation8793.itsofresh.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.databinding.ActivityChangePwBinding

class ChangePwActivity : AppCompatActivity() {
    lateinit var binding : ActivityChangePwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_pw)
        binding.setActionBar("비밀번호 변경")

        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }

        binding.findIdBox.setText(intent.getStringExtra("email"))

        binding.comfirmBtn.setOnClickListener {
            var intent = Intent(this@ChangePwActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }
}