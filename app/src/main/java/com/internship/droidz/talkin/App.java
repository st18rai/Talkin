package com.internship.droidz.talkin;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Log.i("crashlytics","it created");
    }
}
