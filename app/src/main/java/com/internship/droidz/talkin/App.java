package com.internship.droidz.talkin;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.internship.droidz.talkin.utils.BackgroundChecker;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public class   App extends MultiDexApplication {

    private static App instance;
    private BackgroundChecker backgroundChecker;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        backgroundChecker = new BackgroundChecker();
        registerActivityLifecycleCallbacks(backgroundChecker);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    //    Fabric.with(this, new Crashlytics());
        Log.i("crashlytics","it created");

        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm.init(this);
    }

    public static App getApp() {
        return instance;
    }

    public BackgroundChecker getBackgroundChecker() {
        return backgroundChecker;
    }
}
