package com.corporation8793.mealkit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.corporation8793.mealkit.service.PedometerService;

public class RebootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent intent1 = new Intent(context, PedometerService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
                context.startForegroundService(intent1);
            }else{
                context.startService(intent1);
            }
        }
    }
}
