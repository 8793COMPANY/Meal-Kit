package com.corporation8793.mealkit.service;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.corporation8793.mealkit.MainApplication;
import com.corporation8793.mealkit.R;
import com.corporation8793.mealkit.activity.LoginActivity;
import com.corporation8793.mealkit.activity.MainActivity;
import com.corporation8793.mealkit.receiver.ResetPedometer;
import com.corporation8793.mealkit.receiver.ShutdownReceiver;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;

public class PedometerService extends Service implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    private static int mStepDetector=0;
    private ShutdownReceiver receiver = new ShutdownReceiver();


    public PedometerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
       registerReceiver(receiver,intentFilter);

       //
      //  SharedPreferences prefs=getApplicationContext().getSharedPreferences("other", 0);
      //  mStepDetector=prefs.getInt("step",0);
        mStepDetector =9999;
        register_Manbogi();

        // return START_REDELIVER_INTENT;
        initializeNotification();
        MainApplication.instance.resetPedometer();
        return START_STICKY ;
    }


    public void initializeNotification() {

        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
            builder.setSmallIcon(R.mipmap.ic_main);
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.bigText("실행중입니다.");
            style.setBigContentTitle(null);
            style.setSummaryText("서비스 동작중");
            builder.setContentText(null);
            builder.setContentTitle(null);
            builder.setOngoing(true);
            builder.setStyle(style);
            builder.setWhen(0);
            builder.setShowWhen(false);
            Intent notificationIntent = new Intent(this, LoginActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(new NotificationChannel("1", "service_mealkit", NotificationManager.IMPORTANCE_DEFAULT));
            }
            Notification notification = builder.build();
            startForeground(1, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
            builder.setSmallIcon(R.mipmap.ic_main);
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.bigText("실행중입니다.");
            style.setBigContentTitle("안녕하세요");
            style.setSummaryText("서비스 동작중");
            builder.setContentText(null);
            builder.setContentTitle(null);
            builder.setOngoing(true);
            builder.setStyle(style);
            builder.setWhen(0);
            builder.setShowWhen(false);
            Intent notificationIntent = new Intent(this, LoginActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(new NotificationChannel("1", "service_mealkit", NotificationManager.IMPORTANCE_DEFAULT));
            }
            Notification notification = builder.build();
            startForeground(1, notification);
        }
    }


    public  void updateNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2");
            builder.setSmallIcon(R.mipmap.ic_main);
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.bigText("클릭해서 포인트를 받아가세요.");
            style.setBigContentTitle(null);
            style.setSummaryText("서비스 동작중");
            builder.setContentText(null);
            builder.setContentTitle(null);
            builder.setOngoing(true);
            builder.setAutoCancel(true);
            builder.setStyle(style);
            builder.setWhen(0);
            builder.setShowWhen(false);
            Intent notificationIntent = new Intent(this, LoginActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(new NotificationChannel("2", "service_mealkit", NotificationManager.IMPORTANCE_DEFAULT));
            }
            Notification notification = builder.build();
            manager.notify(2,notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2");
            builder.setSmallIcon(R.mipmap.ic_main);
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.bigText("클릭해서 포인트를 받아가세요.");
            style.setBigContentTitle(null);
            style.setSummaryText("서비스 동작중");
            builder.setContentText(null);
            builder.setContentTitle(null);
            builder.setOngoing(true);
            builder.setStyle(style);
            builder.setAutoCancel(true);
            builder.setWhen(0);
            builder.setShowWhen(false);
            Intent notificationIntent = new Intent(this, LoginActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(new NotificationChannel("2", "service_mealkit", NotificationManager.IMPORTANCE_DEFAULT));
            }
            Notification notification = builder.build();
            manager.notify(2,notification);
        }
    }



    public void register_Manbogi() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepDetectorSensor == null) {
        } else {
            sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }



    public static boolean isServiceRunning(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo rsi : am.getRunningServices(Integer.MAX_VALUE)) {
            if (PedometerService.class.getName().equals(rsi.service.getClassName())) //[서비스이름]에 본인 것을 넣는다.
            return true;
        }
        return false;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if (sensorEvent.values[0] == 1.0f) {
                mStepDetector += sensorEvent.values[0];
                if(mStepDetector>= 3000 && MainApplication.instance.getPedometerSuccessCount("3000p") == 0){
                    MainApplication.instance.setPedometerSuccessCount("3000p",1);
                    updateNotification();
                }

                if(mStepDetector>= 5000 && MainApplication.instance.getPedometerSuccessCount("5000p") == 0){
                    MainApplication.instance.setPedometerSuccessCount("5000p",1);
                    updateNotification();
                }

                if(mStepDetector>= 10000 && MainApplication.instance.getPedometerSuccessCount("10000p") == 0){
                    MainApplication.instance.setPedometerSuccessCount("10000p",1);
                    MainApplication.instance.setPedometerSuccessCount("point_roulette",1);
                    updateNotification();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public static int getStep(){
        return mStepDetector;
    }

    public static void resetStep(){
       mStepDetector =  0;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}