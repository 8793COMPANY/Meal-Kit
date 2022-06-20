package com.corporation8793.mealkit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corporation8793.mealkit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.joinText.setOnClickListener {
            var intent = Intent(this@LoginActivity, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            var intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.findIdText.setOnClickListener{
            var intent = Intent(this@LoginActivity, FindActivity::class.java)
            intent.putExtra("title","아이디")
            startActivity(intent)
        }

        binding.findPwText.setOnClickListener{
            var intent = Intent(this@LoginActivity, FindActivity::class.java)
            intent.putExtra("title","비밀번호")
            startActivity(intent)
        }

        binding.autoLogin.setOnClickListener {
            if (binding.accessTermAgreeBtn.isChecked)
                binding.accessTermAgreeBtn.isChecked = false
            else
                binding.accessTermAgreeBtn.isChecked = true

        }
    }
}