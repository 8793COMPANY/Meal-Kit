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
    }
}