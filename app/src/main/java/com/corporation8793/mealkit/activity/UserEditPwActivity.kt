package com.corporation8793.mealkit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityChangePwBinding
import com.corporation8793.mealkit.databinding.ActivityUserEditPwBinding

class UserEditPwActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserEditPwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //     setContentView(R.layout.activity_user_edit_pw)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_edit_pw)
        binding.setActionBar("비밀번호 변경")

        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }
        binding.findIdBox.setText(MainApplication.instance.user.email)

        binding.comfirmBtn.setOnClickListener {
          finish();
        }

    }
}