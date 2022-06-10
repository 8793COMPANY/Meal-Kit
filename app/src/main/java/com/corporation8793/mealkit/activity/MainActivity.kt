package com.corporation8793.mealkit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.ShopInfoDialog
import com.corporation8793.mealkit.databinding.ActivityMainBinding
import com.corporation8793.mealkit.fragment.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        var navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)


    }
}