package com.internship.droidz.talkin.mvp.splash;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Joyker on 18.01.2017.
 */

public class SplashModelImpl implements SplashContract.SplashModel {


    public SplashModelImpl(Context ctx)
    {
        this.ctx=ctx;
        sPref = ctx.getSharedPreferences("SplashPref",MODE_PRIVATE);
    }

    Context ctx;
    SharedPreferences sPref;
    SharedPreferences.Editor ed;

    private static final String LOGGED_IN_KEY = "loggedIn";

    @Override
    public boolean isLoggedIn() {
        return sPref.getBoolean(LOGGED_IN_KEY,true);
    }

    @Override
    public void setLoggedIn(boolean value) {
        ed=sPref.edit();
        ed.putBoolean(LOGGED_IN_KEY,value);
        ed.commit();
    }
}
