package com.internship.droidz.talkin.mvp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.mvp.login.LoginScreen;
import com.internship.droidz.talkin.mvp.main.MainScreen;

import io.fabric.sdk.android.Fabric;

public class SplashScreenView extends AppCompatActivity {

    SplashContract.SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenterImpl(this);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (presenter.checkLoggedIn()) {
                    Intent intent = new Intent(SplashScreenView.this, MainScreen.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreenView.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}
