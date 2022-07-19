package com.corporation8793.itsofresh.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.databinding.ActivityUserEditPwBinding

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