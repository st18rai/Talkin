package com.internship.droidz.talkin.mvp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.mvp.login.LoginScreen;
import com.internship.droidz.talkin.mvp.main.MainScreen;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity implements SplashContract.SplashView{

    // loggedIn must be stored in the model?
    SplashContract.SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenterImpl(new SplashModelImpl(this), this);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.checkLoggedInAndNavigate();
            }
        }, 3000);
    }

    @Override
    public void navigateToMainScreen() {
        Intent intent = new Intent(SplashScreen.this, MainScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }
}
