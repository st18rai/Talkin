package com.internship.droidz.talkin.mvp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.mvp.login.LoginScreen;
import com.internship.droidz.talkin.mvp.main.MainScreen;

public class SplashScreen extends AppCompatActivity implements SplashContract.SplashView{

    SplashContract.SplashPresenter presenter;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenterImpl(new SplashModelImpl(this), this);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                presenter.checkLoggedInAndNavigate();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        handler = null;
        runnable = null;

        super.onPause();
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
