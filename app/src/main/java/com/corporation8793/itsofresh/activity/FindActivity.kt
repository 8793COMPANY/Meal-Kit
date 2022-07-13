package com.corporation8793.itsofresh.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.databinding.ActivityFindBinding

class FindActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindBinding
    lateinit var title : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = intent.getStringExtra("title")!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find)

        binding.setActionBar(title+" 찾기")

        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }
    }

    fun changeTitle(title: String){
        binding.setActionBar(title)
    }

    fun getFind(): Int{
        if (title.equals("아이디")){
            return 0
        }

        return 1
    }

}