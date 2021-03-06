package com.corporation8793.itsofresh.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.databinding.ActivityJoinBinding
import com.corporation8793.itsofresh.esf_wp.rest.data.Billing
import com.corporation8793.itsofresh.esf_wp.rest.data.Meta_data
import com.corporation8793.itsofresh.esf_wp.rest.data.Shipping
import com.corporation8793.itsofresh.esf_wp.rest.data.sign_up.SignUpBody
import com.corporation8793.itsofresh.esf_wp.rest.repository.Board4BaRepository
import com.corporation8793.itsofresh.esf_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    lateinit var recommenderId : String
    var isActiveJoinBtn : Boolean = false;
    var isOverlapCheck : Boolean = false;
    var passwordShowCheck : Boolean = false;
    var passwordCheckShowCheck : Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adminAuth = Credentials.basic("esffnb", "@esfadmin*0967")
        val board4BaRepository = Board4BaRepository(adminAuth)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.title = "회원가입"

        //다음 주소 api 호출
        binding.searchPostCodeBtn.setOnClickListener{
            var intent = Intent(this@JoinActivity, AddreesWebActivity::class.java)
            startActivityForResult(intent,1000)
        }

        binding.goPersonalInfoView.setOnClickListener {
            var intent = Intent(this, AccessTermsActivity::class.java)
            intent.putExtra("type","개인정보처리방침")
            startActivity(intent)
        }


        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }


        binding.idInputBox.addTextChangedListener{
            activeJoinBtn();
        }
        binding.nameInputBox.addTextChangedListener{
            activeJoinBtn();
        }
        binding.phoneNumberInputBox.addTextChangedListener{
            activeJoinBtn();
        }

        binding.emailInputBox.addTextChangedListener{
            activeJoinBtn()
        }

        binding.postCodeInputBox.addTextChangedListener{
            activeJoinBtn()
        }
        binding.addressDetailInputBox.addTextChangedListener{
            activeJoinBtn()
        }







        binding.recommenderCodeCheckBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                val value = NonceRepository().checkUsername(binding.recommenderCodeInputBox.text.toString().trim())
                Log.e("3", value.first);
                Log.e("3", value.second!!.size.toString());


                GlobalScope.launch(Dispatchers.Main) {
                    if(value.second!!.size > 0){
                        binding.recommenderErrorText.setText("추천인이 확인 되었습니다.")
                        recommenderId = value.second!!.filter { customer -> customer.username == "${binding.recommenderCodeInputBox.text.toString().trim()}" }.first().id
                        binding.recommenderErrorText.setTextColor(resources.getColor(R.color.app_basic_color))
                        activeJoinBtn()
                       }else{
                        binding.recommenderErrorText.setTextColor(resources.getColor(R.color.red_ce2929))
                        binding.recommenderErrorText.setText("추천인을 확인 할 수 없습니다.")

                       }
                }
//                binding.checkText.visibility = View.VISIBLE
            }
        }
        binding.joinPasswordShowBtn.setOnClickListener{
            if(!passwordShowCheck){

                binding.pwInputBox.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.joinPasswordShowBtn.setBackgroundDrawable(getDrawable(R.drawable.join_password_show_image))
                passwordShowCheck = true
            }else{
                binding.pwInputBox.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.joinPasswordShowBtn.setBackgroundDrawable(getDrawable(R.drawable.join_password_hide_image))
                passwordShowCheck =false;
            }
        }

        binding.joinPasswordcheckShowBtn.setOnClickListener{
            if(!passwordCheckShowCheck){

                binding.pwCheckInputBox.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.joinPasswordcheckShowBtn.setBackgroundDrawable(getDrawable(R.drawable.join_password_show_image))
                passwordCheckShowCheck = true

            }else{
                binding.pwCheckInputBox.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.joinPasswordcheckShowBtn.setBackgroundDrawable(getDrawable(R.drawable.join_password_hide_image))
                passwordCheckShowCheck =false;
            }
        }


        binding.pwInputBox.addTextChangedListener{
            if(binding.pwInputBox.text.length<5){
                binding.joinPasswordErrorText.setTextColor(resources.getColor(R.color.red_ce2929))
                binding.joinPasswordErrorText.setText("4자리 이상 입력해주세요.")
                activeJoinBtn()
                return@addTextChangedListener
            }

            if(binding.pwInputBox.text.toString().equals(binding.pwCheckInputBox.text.toString())){
                binding.joinPasswordErrorText.setTextColor(resources.getColor(R.color.app_basic_color))
                binding.joinPasswordErrorText.setText("사용 가능합니다.")
                activeJoinBtn()

            }else{
                binding.joinPasswordErrorText.setTextColor(resources.getColor(R.color.red_ce2929))
                binding.joinPasswordErrorText.setText("비밀번호가 동일하지 않습니다.")
                activeJoinBtn()
            }

        }

        binding.pwCheckInputBox.addTextChangedListener{
            if(binding.pwInputBox.text.toString().equals(binding.pwCheckInputBox.text.toString())){
                binding.joinPasswordErrorText.setTextColor(resources.getColor(R.color.app_basic_color))
                binding.joinPasswordErrorText.setText("사용 가능합니다.")
                activeJoinBtn()
            }else{
                binding.joinPasswordErrorText.setTextColor(resources.getColor(R.color.red_ce2929))
                binding.joinPasswordErrorText.setText("비밀번호가 동일하지 않습니다.")
                activeJoinBtn()
            }
        }




        binding.overlapCheckBtn.setOnClickListener {
            val id = binding.idInputBox.text.toString()
            if(id.trim().equals("")){
                binding.overlapCheckText.setText("아이디를 입력해주세요.")
            }else if(id.length < 5){
                binding.overlapCheckText.setText("아이디를 다섯글자이상 입력해주세요.")
            }else {
                GlobalScope.launch(Dispatchers.Default) {
                    val value = NonceRepository().checkUsername(binding.idInputBox.text.toString().trim())
                    GlobalScope.launch(Dispatchers.Main) {
                        binding.overlapCheckText.visibility = View.VISIBLE
                        if (value.second!!.size > 0) {
                            binding.overlapCheckText.setText("이미 사용중인 아이디입니다.")
                            binding.overlapCheckText.setTextColor(resources.getColor(R.color.red_ce2929))
                            isOverlapCheck = false;
                        } else {
                            binding.overlapCheckText.setText("사용가능한 아이디입니다.")
                            binding.overlapCheckText.setTextColor(resources.getColor(R.color.app_basic_color))
                            isOverlapCheck = true;
                            activeJoinBtn();
                        }

                    }
//                binding.checkText.visibility = View.VISIBLE
                }
            }
        }

        binding.joinBtn.setOnClickListener {

            if(!isActiveJoinBtn){
                return@setOnClickListener
            }
            binding.joinProgress.visibility=View.VISIBLE
            GlobalScope.launch(Dispatchers.Default) {
                val value = NonceRepository().runSignUp(
                        binding.emailInputBox.text.toString().trim(),
                        binding.idInputBox.text.toString().trim(),
                        binding.pwInputBox.text.toString().trim(),
                        binding.nameInputBox.text.toString().trim(),
                        SignUpBody(
                        Billing(binding.nameInputBox.text.toString().trim(), binding.addressInputBox.text.toString(), binding.addressDetailInputBox.text.toString(), binding.postCodeInputBox.text.toString(), binding.phoneNumberInputBox.text.toString()),
                        Shipping(binding.nameInputBox.text.toString().trim(), binding.addressInputBox.text.toString(), binding.addressDetailInputBox.text.toString(),  binding.postCodeInputBox.text.toString(), binding.phoneNumberInputBox.text.toString()),
                        arrayOf(
                            Meta_data(id = null, key = "recommender", value = binding.recommenderCodeInputBox.text.toString()),
                            Meta_data(id = null, key = "point", value = 0),
                            Meta_data(id = null, key = "profile_img", value = "http://eatsofresh.co.kr/wp-content/uploads/woocommerce-placeholder.png")
                            )
                        )
                )

               println("value ==== "+value)
                Log.e("value ==== ",value.first)

                GlobalScope.launch(Dispatchers.Main) {
                    binding.joinProgress.visibility=View.GONE
                    if(value.first.toString().equals("504")){
                        Toast.makeText(this@JoinActivity, "올바르지 않은 이메일 형식입니다.", Toast.LENGTH_SHORT).show()
                    }

                    if(value.first.toString().equals("501")){
                            Toast.makeText(this@JoinActivity, " 이메일 중복입니다.", Toast.LENGTH_SHORT).show()
                    }

                    if(value.first.toString().equals("502")){
                        Toast.makeText(this@JoinActivity, "아이디 중복입니다.", Toast.LENGTH_SHORT).show()
                    }

                    if(value.first.toString().equals("201")){
                        Toast.makeText(this@JoinActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                        GlobalScope.launch(Dispatchers.Default) {
                            Log.e("JoinActivity", "recommender ID: $recommenderId")
                            if (recommenderId != null) {
                                NonceRepository().editPoint(
                                    b4ba = board4BaRepository,
                                    id = recommenderId,
                                    point = 150,
                                    action = "recommender",
                                    log = "${binding.idInputBox.text.toString().trim()}님의 추천"
                                )
                            } else {
                                Log.e("JoinActivity", "no recommender")
                            }

                            GlobalScope.launch(Dispatchers.Main) {
                                finish()
                            }
                        }
                    }
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


    fun activeJoinBtn(){
        binding.joinBtn.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.gray_dddddd))
        isActiveJoinBtn = false

        if(binding.idInputBox.text.length == 0){
            return
        }
        if(!isOverlapCheck){
            return
        }
        if(binding.nameInputBox.text.length == 0){
            return
        }
      if(!binding.pwInputBox.text.toString().equals(binding.pwCheckInputBox.text.toString())){
          return
      }

        if(binding.phoneNumberInputBox.text.length == 0){
            return
        }
        if(binding.emailInputBox.text.length == 0){
            return
        }

        if(binding.postCodeInputBox.text.length == 0){
            return
        }

//
//        if(!binding.accessTermBtn.isChecked){
//            return
//        }

        binding.joinBtn.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.green_009658))
        isActiveJoinBtn = true
    }

}