package com.corporation8793.mealkit

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.corporation8793.mealkit.esf_wp.rest.data.Customer
import com.corporation8793.mealkit.receiver.ResetPedometer
import java.util.*

class MainApplication : Application() {


    lateinit var user:Customer;
    override fun onCreate() {
        super.onCreate()
        // initialize Rudder SDK here
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


    fun resetPedometer() {
        val intent = Intent(this, ResetPedometer::class.java)
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
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




}
