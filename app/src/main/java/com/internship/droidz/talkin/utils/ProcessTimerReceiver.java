package com.internship.droidz.talkin.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.ui.activity.login.LoginActivity;

/**
 * Created by Koroqe on 29-Jan-17.
 */

public class ProcessTimerReceiver extends BroadcastReceiver {

    String TAG = "ProcessTimerReceiver";
    public static final int NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Notification");
        createNotification(context, context.getString(R.string.app_name),
                context.getString(R.string.login_notification));
    }

    private void createNotification(Context context, String title, String text) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, LoginActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(notificationIntent)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_SOUND);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}