package com.corporation8793.mealkit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityChangePwBinding
import com.corporation8793.mealkit.databinding.ActivityWriteRecipeBinding

class WriteRecipeActivity : AppCompatActivity() {
    lateinit var binding : ActivityWriteRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_recipe)
        binding.setActionBar("레시피작성")

        binding.actionBar.backBtn.setOnClickListener {
            finish()
        }

    }
}