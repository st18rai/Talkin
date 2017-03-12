package com.internship.droidz.talkin.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.utils.ProcessTimerReceiver;

/**
 * Created by st18r on 10.02.2017.
 */

public class LoginModel {

    public static final int TIME_TO_SEND_NOTIFICATION = 15 * 60;

    Context context = App.getApp().getApplicationContext();

    public void checkAndStartTimer() {

        if (!App.getApp().getBackgroundChecker().isAppInForeground()) {
            Log.i("TAG", "checkAndStartTimer: ");
            AlarmManager processTimer = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent intent = new Intent(context, ProcessTimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            processTimer.set(AlarmManager.RTC, System.currentTimeMillis() + TIME_TO_SEND_NOTIFICATION * 1000, pendingIntent);
        }
    }

    public void stopTimer() {

        Log.i("TAG", "stopTimer: ");
        Intent intent = new Intent(context, ProcessTimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
