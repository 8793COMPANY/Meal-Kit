package com.corporation8793.mealkit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityJoinBinding
import com.corporation8793.mealkit.esf_wp.rest.data.Billing
import com.corporation8793.mealkit.esf_wp.rest.data.Meta_data
import com.corporation8793.mealkit.esf_wp.rest.data.Shipping
import com.corporation8793.mealkit.esf_wp.rest.data.sign_up.SignUpBody
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.title = "회원가입"

        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }

        binding.overlapCheckBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                val value = NonceRepository().checkUsername(binding.idInputBox.text.toString().trim())
                println("value : " + value)
                println("value first: " + value.first)
                println("value second: " + value.second!!.size)



                GlobalScope.launch(Dispatchers.Main) {
                    binding.checkText.visibility = View.VISIBLE
                    if(value.second!!.size > 0){
                        binding.checkText.setText("이미 사용중인 아이디입니다.")
                        binding.checkText.setTextColor(resources.getColor(R.color.red_ce2929))
                    }else{
                        binding.checkText.setText("사용가능한 아이디입니다.")
                        binding.checkText.setTextColor(resources.getColor(R.color.gray_4e4e4e))
                    }

                }
//                binding.checkText.visibility = View.VISIBLE
            }
        }

        binding.joinBtn.setOnClickListener {

            GlobalScope.launch(Dispatchers.Default) {
                val value = NonceRepository().runSignUp(
                        binding.emailInputBox.text.toString().trim(),
                        binding.idInputBox.text.toString().trim(),
                        binding.pwInputBox.text.toString().trim(),
                        binding.nameInputBox.text.toString().trim(),
                        SignUpBody(
                        Billing("광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", binding.phoneNumberInputBox.text.toString()),
                        Shipping("광주광역시 동구 동계천로 150", "502호, 팔칠구삼", "143-78", binding.phoneNumberInputBox.text.toString()),
                        arrayOf(Meta_data(id = null, key = "recommender", value = "test2"))
                        )
                )

                println(value.first)
                println(value.second?.id)

                GlobalScope.launch(Dispatchers.Main) {
//                    binding.checkText.visibility = View.VISIBLE
//                    if(value.second!!.size > 0){
//
//                    }else{
//
//                    }

                }
//                binding.checkText.visibility = View.VISIBLE
            }
        }
    }
}