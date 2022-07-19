package com.corporation8793.itsofresh.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.corporation8793.itsofresh.MainApplication
import com.corporation8793.itsofresh.R
import com.corporation8793.itsofresh.databinding.ActivityMainBinding
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

    override fun onResume() {
        super.onResume()
        Log.e("main","onresume")
        MainApplication.instance.registerNetworkCallback()
    }

    override fun onStop() {
        super.onStop()
        Log.e("main","onstop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("main","ondestory")
                MainApplication.instance.unregisterNetworkCallback()
    }
}