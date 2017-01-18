package com.internship.droidz.talkin.mvp.splash;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.internship.droidz.talkin.R;

import io.fabric.sdk.android.Fabric;

public class SplashScreenView extends AppCompatActivity {

    // loggedIn must be stored in the model?
    SplashPresenterImpl presenter;

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
                    presenter.navigateToMain();
                    finish();
                }
                else {
                    presenter.navigateToLogin();
                    finish();
                }
            }
        }, 3000);
    }
}
