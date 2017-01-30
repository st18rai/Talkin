package com.internship.droidz.talkin.mvp.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.internship.droidz.talkin.utils.BackgroundChecker;

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
        instance = this;
        Fabric.with(this, new Crashlytics());
        backgroundChecker = new BackgroundChecker();
        registerActivityLifecycleCallbacks(backgroundChecker);
    }

    public BackgroundChecker getBackgroundChecker() {
        return backgroundChecker;
    }
}
