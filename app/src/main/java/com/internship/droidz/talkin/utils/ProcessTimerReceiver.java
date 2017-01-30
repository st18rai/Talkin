package com.internship.droidz.talkin.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Koroqe on 29-Jan-17.
 */

public class ProcessTimerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "TIKTAK");
        //Send GCM
    }
}