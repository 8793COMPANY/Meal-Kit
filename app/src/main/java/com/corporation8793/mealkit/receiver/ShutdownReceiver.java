package com.corporation8793.mealkit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.corporation8793.mealkit.service.PedometerService;

public class ShutdownReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("test","Test");

     SharedPreferences preferences  = context.getSharedPreferences("other", 0);
     SharedPreferences.Editor edit=preferences.edit();
     edit.putInt("step", PedometerService.getStep());
     edit.apply();
    }
}
