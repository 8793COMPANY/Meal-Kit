package com.corporation8793.mealkit.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityUserEditBinding
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UserEditActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_edit)
        binding.title="회원정보 수정"



        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }

        binding.idInputBox.setText(MainApplication.instance.user.username)

        binding.nameInputBox.setText(MainApplication.instance.user.first_name)

        binding.phoneNumberInputBox.setText(MainApplication.instance.user.shipping.phone)

        binding.emailInputBox.setText(MainApplication.instance.user.email)
        //setContentView(R.layout.activity_user_edit)
        binding.postCodeInputBox.setText(MainApplication.instance.user.shipping.postcode)
        binding.addressInputBox.setText(MainApplication.instance.user.shipping.address_1)
        binding.addressDetailInputBox.setText(MainApplication.instance.user.shipping.address_2)
        binding.recommenderCodeInputBox.setText(MainApplication.instance.user.meta_data.filter { metaData -> metaData.key == "recommender" }.first().value.toString())
        binding.editUserSearchPostCodeBtn.setOnClickListener{
            var intent = Intent(this@UserEditActivity, AddreesWebActivity::class.java)
            startActivityForResult(intent,1000)
        }

        binding.editUserPasswordBtn.setOnClickListener{

            GlobalScope.launch(Dispatchers.Default) {
                val value = NonceRepository().sendPassResetLink(MainApplication.instance.user.username)
                val status = value.second?.status
                if (status.equals("ok")) {
                    var intent = Intent(this@UserEditActivity, UserEditPwActivity::class.java)
                    startActivity(intent)
                }

                GlobalScope.launch(Dispatchers.Main) {
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                1000 -> {
                    val address = data?.getStringExtra("data")!!.split(",")
                    if (address != null) {
                        binding.postCodeInputBox.setText(address[0])
                        binding.addressInputBox.setText(address[1])
                    }
                }
            }
        }
    }
}