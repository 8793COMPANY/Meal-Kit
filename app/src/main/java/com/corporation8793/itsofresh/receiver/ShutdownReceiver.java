package com.corporation8793.itsofresh.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.corporation8793.itsofresh.service.PedometerService;

public class ShutdownReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
     SharedPreferences preferences  = context.getSharedPreferences("other", 0);
     SharedPreferences.Editor edit=preferences.edit();
     edit.putInt("step", PedometerService.getStep());
     edit.apply();
    }
}
