package com.corporation8793.mealkit

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.corporation8793.mealkit.activity.MainActivity
import com.corporation8793.mealkit.activity.NetworkCheckActivity
import com.corporation8793.mealkit.activity.WriteRecipeActivity
import com.corporation8793.mealkit.esf_wp.rest.data.Customer
import com.corporation8793.mealkit.esf_wp.rest.repository.Board4BaRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.BoardRepository
import com.corporation8793.mealkit.esf_wp.rest.repository.NonceRepository
import com.corporation8793.mealkit.receiver.ResetPedometer
import java.util.*

class MainApplication : Application() {


    lateinit var user:Customer;

    var nonceRepository = NonceRepository()

    lateinit var board4BaRepository : Board4BaRepository
    lateinit var boardRepository: BoardRepository
    override fun onCreate() {
        super.onCreate()
//        registerNetworkCallback()
        // initialize Rudder SDK here
    }

    override fun onTerminate() {
        super.onTerminate()
    }





    init{
        instance = this
    }

    companion object {
        lateinit var instance: MainApplication
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }


    fun setCustomer(customer: Customer?){
        if (customer != null) {
            user = customer
        };
    }


    fun setAuth(auth: String?){
        if (auth != null) {
            board4BaRepository = Board4BaRepository(auth);
            boardRepository = BoardRepository();
        };
    }



    fun setPedometerSuccessCount(name: String?, type: Int?){
        if (name != null && type != null) {
            val sharedPreference = getSharedPreferences("other", 0)
            val editor = sharedPreference.edit()
            editor.putInt(name,type)
            editor.apply()
        };
    }



    fun getPedometerSuccessCount(name: String?):Int{
        val sharedPreference = getSharedPreferences("other", 0)
        return sharedPreference.getInt(name,0);
    }





    fun resetPedometer() {
        val intent = Intent(this, ResetPedometer::class.java)
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val calendar = Calendar.getInstance()
        if (calendar[Calendar.HOUR_OF_DAY] > 23) {
            calendar.add(Calendar.DATE, 1)
        } else if (calendar[Calendar.HOUR_OF_DAY] == 23) {
            if (calendar[Calendar.MINUTE] >= 59) {
                calendar.add(Calendar.DATE, 1)
            }
        }
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 0
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmClockInfo(calendar.timeInMillis, alarmIntent),
                alarmIntent
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
            } else {
                alarmManager[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = alarmIntent
            }
        }
    }

     val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.e("Test", "wifi available")
//            Toast.makeText(applicationContext,"on available",Toast.LENGTH_SHORT).show()
        }

        override fun onLost(network: Network) {
            Log.e("Test", "wifi onlost")
//            Toast.makeText(applicationContext,"on lost",Toast.LENGTH_SHORT).show()
            var intent = Intent(applicationContext, NetworkCheckActivity::class.java)
            startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK))

        }
    }

     fun registerNetworkCallback() {
        val cm = getSystemService(ConnectivityManager::class.java)
        val wifiNetworkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
        cm.registerNetworkCallback(wifiNetworkRequest, networkCallback)
    }

     fun unregisterNetworkCallback() {
        val cm = getSystemService(ConnectivityManager::class.java)
        cm.unregisterNetworkCallback(networkCallback)
    }

    fun isWIFIConnected(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = false
            }
        }
        return result
    }




}
