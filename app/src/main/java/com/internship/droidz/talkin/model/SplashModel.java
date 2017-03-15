package com.internship.droidz.talkin.model;

import android.content.SharedPreferences;

import com.internship.droidz.talkin.App;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by st18r on 10.02.2017.
 */

public class SplashModel {

    public SplashModel() {

        sPref = App.getApp().getApplicationContext().getSharedPreferences("SplashPref", MODE_PRIVATE);
    }

    private SharedPreferences sPref;
    private SharedPreferences.Editor ed;

    private static final String LOGGED_IN_KEY = "loggedIn";

    public boolean isLoggedIn() {
        return sPref.getBoolean(LOGGED_IN_KEY, false);
    }

    public void setLoggedIn(boolean value) {

        ed = sPref.edit();
        ed.putBoolean(LOGGED_IN_KEY, value);
        ed.commit();
    }
}
