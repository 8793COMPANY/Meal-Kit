package com.corporation8793.mealkit.activity

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.databinding.ActivityLoginBinding
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("other", 0)

        if(sharedPreference.getBoolean("autoLogin",false)){
            binding.idInputBox.setText(sharedPreference.getString("id","").toString())
            binding.pwInputBox.setText(sharedPreference.getString("pw","").toString())

            call_Login(sharedPreference.getString("id","").toString(),sharedPreference.getString("pw","").toString())
            binding.accessTermAgreeBtn.isChecked = true;
        }else{
            binding.accessTermAgreeBtn.isChecked = false;
        }

        binding.joinText.setOnClickListener {
            var intent = Intent(this@LoginActivity, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            call_Login(binding.idInputBox.text.toString(),binding.pwInputBox.text.toString());
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

    fun call_Login(user:String,password:String){
        binding.loginProgress.visibility= View.VISIBLE
        GlobalScope.launch(Dispatchers.Default) {
            //여기서 라이브러리 작업  (여기서 레이아웃 작업 실행 못함)
            val value = NonceRepository().Login(username = user, password =password)

            GlobalScope.launch(Dispatchers.Main) {
                if(value.first.equals("ok")){
                    MainApplication.instance.setCustomer(value.second)
                    binding.loginProgress.visibility = View.GONE
                }else{
                    Toast.makeText(this@LoginActivity,"로그인이 실패하였습니다",Toast.LENGTH_LONG).show();
                    binding.loginProgress.visibility = View.GONE
                    return@launch
                }

                if(binding.accessTermAgreeBtn.isChecked){
                    val sharedPreference = getSharedPreferences("other", 0)
                    val editor = sharedPreference.edit()
                    editor.putString("id", binding.idInputBox.text.toString())
                    editor.putString("pw", binding.pwInputBox.text.toString())
                    editor.putBoolean("autoLogin",true)
                    editor.apply()
                }else{
                    val sharedPreference = getSharedPreferences("other", 0)
                    val editor = sharedPreference.edit()
                    editor.putString("id", binding.idInputBox.text.toString())
                    editor.putString("pw", binding.pwInputBox.text.toString())
                    editor.putBoolean("autoLogin",false)
                    editor.apply()
                }

                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()


            }
        }
    }
}