package com.corporation8793.mealkit

import android.app.Application
import android.content.Context
import com.corporation8793.mealkit.esf_wp.rest.data.Customer

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
}
