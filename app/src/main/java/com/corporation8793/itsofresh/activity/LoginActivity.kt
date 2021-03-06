package com.corporation8793.itsofresh.activity

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.databinding.ActivityLoginBinding
import com.corporation8793.itsofresh.esf_wp.rest.repository.NonceRepository
import com.corporation8793.itsofresh.service.PedometerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Toast.makeText(this@LoginActivity,"Permission granted",1000).show()
            if (!PedometerService.isServiceRunning(this)) {
                Log.e("서비스중이 아님", "서비스중이 아님");
                val intent = Intent(this, PedometerService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent)
                }


            }
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), // 1
                1000
            ) // 2
        }

        val sharedPreference = getSharedPreferences("other", 0)

        if (sharedPreference.getBoolean("autoLogin", false)) {
            binding.idInputBox.setText(sharedPreference.getString("id","").toString())
            binding.pwInputBox.setText(sharedPreference.getString("pw","").toString())

            if(MainApplication.instance.isWIFIConnected(this))
                call_Login(binding.idInputBox.text.toString(), binding.pwInputBox.text.toString());

            binding.accessTermAgreeBtn.isChecked = true;
        } else {
            binding.accessTermAgreeBtn.isChecked = false;
        }

        binding.joinText.setOnClickListener {
            var intent = Intent(this@LoginActivity, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            if(MainApplication.instance.isWIFIConnected(this))
                call_Login(binding.idInputBox.text.toString(), binding.pwInputBox.text.toString());
            else
                Toast.makeText(applicationContext,"네트워크를 확인해주세요.",Toast.LENGTH_SHORT).show()
        }

        binding.findIdText.setOnClickListener {
            var intent = Intent(this@LoginActivity, FindActivity::class.java)
            intent.putExtra("title", "아이디")
            startActivity(intent)
        }

        binding.findPwText.setOnClickListener {
            var intent = Intent(this@LoginActivity, FindActivity::class.java)
            intent.putExtra("title", "비밀번호")
            startActivity(intent)
        }

        binding.autoLogin.setOnClickListener {
            if (binding.accessTermAgreeBtn.isChecked)
                binding.accessTermAgreeBtn.isChecked = false
            else
                binding.accessTermAgreeBtn.isChecked = true

        }


    }

    fun call_Login(user: String, password: String) {
        binding.loginProgress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Default) {
            //여기서 라이브러리 작업  (여기서 레이아웃 작업 실행 못함)
            val value = NonceRepository().Login(username = user, password = password)

            GlobalScope.launch(Dispatchers.Main) {
                if (value.first.equals("ok")) {
                    MainApplication.instance.setCustomer(value.second)
                    Log.e("call_Login - current user data : ", "${value.second}")
                    val Id = binding.idInputBox.text.toString()
                    val Pw = binding.pwInputBox.text.toString()
                    val basicAuth = Credentials.basic(Id, Pw)
                    MainApplication.instance.setAuth(basicAuth)
                    binding.loginProgress.visibility = View.GONE
                } else {
                    Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다", Toast.LENGTH_LONG).show();
                    binding.loginProgress.visibility = View.GONE
                    return@launch
                }

                if (binding.accessTermAgreeBtn.isChecked) {
                    val sharedPreference = getSharedPreferences("other", 0)
                    val editor = sharedPreference.edit()
                    editor.putString("id", binding.idInputBox.text.toString())
                    editor.putString("pw", binding.pwInputBox.text.toString())
                    editor.putBoolean("autoLogin", true)
                    editor.apply()
                } else {
                    val sharedPreference = getSharedPreferences("other", 0)
                    val editor = sharedPreference.edit()
                    editor.putString("id", "")
                    editor.putString("pw", "")
                    editor.putBoolean("autoLogin", false)
                    editor.apply()
                }

                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()


            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {  // 1
                if (grantResults.isEmpty()) {  // 2
                    throw RuntimeException("Empty permission result")
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {  // 3
                    Toast.makeText(this@LoginActivity, "권한이 수락되었습니다.", 1000).show()
                    if (!PedometerService.isServiceRunning(this)) {
                        val intent = Intent(this, PedometerService::class.java)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(intent);
                        } else {
                            startService(intent)
                        }
                    }
                } else {
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.ACTIVITY_RECOGNITION
                        )
                    ) { // 4
                        Log.d(TAG, "User declined, but i can still ask for more")
                        requestPermissions(
                            arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                            1000
                        )
                    } else {
                        Log.d(TAG, "User declined and i can't ask")
                        showDialogToGetPermission()   // 5
                    }
                }
            }
        }
    }


    private fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("권한 요청")
            .setMessage("권한을 부여하려면 설정으로 이동해야 합니다.")

        builder.setPositiveButton("이동") { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)   // 6
        }
        builder.setNegativeButton("나중에") { dialogInterface, i ->
            // ignore
        }
        val dialog = builder.create()
        dialog.show()
    }






}