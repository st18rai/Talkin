package com.internship.droidz.talkin.mvp.app;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.internship.droidz.talkin.utils.BackgroundChecker;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public class App extends Application {

    private static App instance;

    private BackgroundChecker backgroundChecker;

    public static App getApp() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        instance = this;
        Fabric.with(this, new Crashlytics());
        Log.i("crashlytics","it created");
        backgroundChecker = new BackgroundChecker();
        registerActivityLifecycleCallbacks(backgroundChecker);
    }

    public BackgroundChecker getBackgroundChecker() {
        return backgroundChecker;
    }
}
