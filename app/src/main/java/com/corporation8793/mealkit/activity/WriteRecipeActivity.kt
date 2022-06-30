package com.corporation8793.mealkit.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityChangePwBinding
import com.corporation8793.mealkit.databinding.ActivityWriteRecipeBinding
import com.corporation8793.mealkit.dto.KitItem
import com.corporation8793.mealkit.esf_wp.rest.RestClient
import com.corporation8793.mealkit.esf_wp.rest.data.Product
import com.corporation8793.mealkit.esf_wp.rest.repository.Board4BaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import java.io.File

class WriteRecipeActivity : AppCompatActivity() {
    lateinit var binding : ActivityWriteRecipeBinding

    val PERMISSION_Album = 101
    val REQUEST_STORAGE = 100

    var img_check : Boolean = false

    lateinit var img_uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_recipe)
        binding.setActionBar("레시피작성")

        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }

        binding.registerBtn.setOnClickListener{
            if (!img_check){
                Toast.makeText(this, "이미지를 등록해주세요", Toast.LENGTH_SHORT).show()
            }else if (binding.recipeName.text.toString().trim().equals("")){
                Toast.makeText(this, "레시피 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if (binding.useMealkit.text.toString().trim().equals("")){
                Toast.makeText(this, "사용한 밀키트를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if (binding.recipeIntrodution.text.toString().trim().equals("")){
                Toast.makeText(this, "레시피 소개글을 작성해주세요.", Toast.LENGTH_SHORT).show()
            }else if (binding.writeRecipe.text.toString().trim().equals("")){
                Toast.makeText(this, "레시피 작성해주세요.", Toast.LENGTH_SHORT).show()
            }else {
                binding.recipeRegisterProgress.visibility = View.VISIBLE
                val sharedPreference = getSharedPreferences("other", 0)
                GlobalScope.launch(Dispatchers.Default) {
                    uploadRecipe(sharedPreference.getString("id", "test22").toString(), sharedPreference.getString("pw", "1234").toString())
                    GlobalScope.launch(Dispatchers.Main) {
                        binding.recipeRegisterProgress.visibility = View.GONE
                        val intent = Intent(this@WriteRecipeActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }

        binding.recipeIntrodution.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.recipeIntrodutionLetterLimitCount.setText(p0!!.length.toString()+"/"+"300")
                if (p0!!.length == 300) {

                    binding.recipeIntrodutionLetterLimitCount.setTextColor(resources.getColor(R.color.red_ce2929))
                }else{
                    binding.recipeIntrodutionLetterLimitCount.setTextColor(resources.getColor(R.color.black))
                }
            }

        })

        binding.recipeImg.setOnClickListener {
            requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
        }


        GlobalScope.launch(Dispatchers.Default) {


            GlobalScope.launch(Dispatchers.Main) {
            }

        }

    }

    fun uploadRecipe(id : String, pw : String){
        try {
            val testId = id
            val testPw = pw

            val basicAuth = Credentials.basic(testId, testPw)
            val boardRepository = Board4BaRepository(basicAuth)

            val file = File(getPath(img_uri))


            val responseMedia = boardRepository.uploadMedia(file!!)

            println("response Media URL : ${responseMedia.first}")
            println("response Media URL : ${responseMedia.second?.guid?.rendered}")
            println("response Media ID : ${responseMedia.second?.id}")
            println("response Media ID : ${responseMedia.second?.date}")

            val responseCode = boardRepository.createPost(
            featured_media = responseMedia.second?.id,
            title = binding.recipeName.text.toString(),
            excerpt = binding.recipeIntrodution.text.toString(),
            content = binding.writeRecipe.text.toString(),
            )

            val addMeta = boardRepository.updatePostAcf(
                    responseCode?.id,
                    binding.useMealkit.text.toString()
            )

            println("response Code : $responseCode\n")
            println("response Code : $addMeta\n")



        }catch (e: Exception){
            Log.e("e",e.toString())
        }

    }

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            // isAllPermissionsGranted : 권한이 모두 승인 되었는지 여부 저장
            // all 메서드를 사용하면 배열 속에 들어 있는 모든 값을 체크할 수 있다.
            val isAllPermissionsGranted =
                    permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }





    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    private fun permissionGranted(requestCode: Int) {
        when (requestCode) {
//            PERMISSION_CAMERA -> openCamera()
            PERMISSION_Album -> openGallery()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
//            PERMISSION_CAMERA -> Toast.makeText(
//                    this,
//                    "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
//                    Toast.LENGTH_LONG
//            ).show()

            PERMISSION_Album -> Toast.makeText(
                    this,
                    "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.",
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_STORAGE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
//                REQUEST_CAMERA -> {
//                    realUri?.let { uri ->
//                        imageView.setImageURI(uri)
//                    }
//                }
                REQUEST_STORAGE -> {
                    data?.data?.let { uri ->
                        if (binding.registerIcon.visibility == View.VISIBLE) {
                            binding.registerIcon.visibility = View.GONE
                            binding.registerText.visibility = View.GONE
                        }
                        img_uri = uri
                        binding.recipeImg.setImageURI(uri)
                        img_check = true
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MainApplication.instance.registerNetworkCallback()
    }

    override fun onStop() {
        super.onStop()
        MainApplication.instance.unregisterNetworkCallback()
    }
}