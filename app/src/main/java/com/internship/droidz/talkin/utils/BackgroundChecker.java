package com.internship.droidz.talkin.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Koroqe on 29-Jan-17.
 */
public class BackgroundChecker implements Application.ActivityLifecycleCallbacks {

    private int countOfStartedActivities = 0;

    public boolean isAppInForeground() {
        return countOfStartedActivities > 0;
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++countOfStartedActivities;
        Log.i("TAG", "onActivityStarted: " + countOfStartedActivities);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        --countOfStartedActivities;
        Log.i("TAG", "onActivityStopped: " + countOfStartedActivities);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}