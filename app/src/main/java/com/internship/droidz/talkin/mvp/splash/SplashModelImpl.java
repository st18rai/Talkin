package com.internship.droidz.talkin.mvp.splash;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Joyker on 18.01.2017.
 */

public class SplashModelImpl implements SplashContract.SplashModel {

    public SplashModelImpl(Context context)
    {
        this.context = context;
        sPref = context.getSharedPreferences("SplashPref", MODE_PRIVATE);
    }

    Context context;
    SharedPreferences sPref;
    SharedPreferences.Editor ed;

    private static final String LOGGED_IN_KEY = "loggedIn";

    @Override
    public boolean isLoggedIn() {
        return sPref.getBoolean(LOGGED_IN_KEY, false);
    }

    @Override
    public void setLoggedIn(boolean value) {
        ed = sPref.edit();
        ed.putBoolean(LOGGED_IN_KEY, value);
        ed.commit();
    }
}
