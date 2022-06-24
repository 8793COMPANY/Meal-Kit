package com.corporation8793.mealkit.activity

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.corporation8793.mealkit.MainApplication
import com.corporation8793.mealkit.R
import com.corporation8793.mealkit.databinding.ActivityMainBinding
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import com.corporation8793.mealkit.receiver.ResetPedometer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        var navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)


        if(MainApplication.instance.getPedometerSuccessCount("3000p") == 1){

            GlobalScope.launch(Dispatchers.Default) {
               val pointResult= MainApplication.instance.nonceRepository.editPoint(MainApplication.instance.board4BaRepository,MainApplication.instance.user.id,30,"+","만보기 3000 걸음")
                if(pointResult.first.equals("200")){
                    MainApplication.instance.setPedometerSuccessCount("3000p",2);
                }
            }
        }

        if(MainApplication.instance.getPedometerSuccessCount("5000p") == 1){

            GlobalScope.launch(Dispatchers.Default) {
                val pointResult= MainApplication.instance.nonceRepository.editPoint(MainApplication.instance.board4BaRepository,MainApplication.instance.user.id,50,"+","만보기 5000 걸음")
                if(pointResult.first.equals("200")){
                    MainApplication.instance.setPedometerSuccessCount("5000p",2);
                }
            }
        }


        if(MainApplication.instance.getPedometerSuccessCount("10000p") == 1) {

            GlobalScope.launch(Dispatchers.Default) {
                val pointResult = MainApplication.instance.nonceRepository.editPoint(
                    MainApplication.instance.board4BaRepository,
                    MainApplication.instance.user.id,
                    100,
                    "+",
                    "만보기 10000 걸음"
                )
                if (pointResult.first.equals("200")) {
                    MainApplication.instance.setPedometerSuccessCount("10000p", 2);

                }
            }

        }
    }
}