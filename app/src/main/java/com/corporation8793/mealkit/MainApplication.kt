package com.corporation8793.mealkit

import android.app.Application
import android.content.Context

class MainApplication : Application() {

  var test ="aa";
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
}
