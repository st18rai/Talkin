package com.internship.droidz.talkin.ui.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.presentation.presenter.splash.SplashPresenter;
import com.internship.droidz.talkin.presentation.view.splash.SplashView;
import com.internship.droidz.talkin.ui.activity.login.LoginActivity;
import com.internship.droidz.talkin.ui.activity.main.MainActivity;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    @InjectPresenter
    SplashPresenter mSplashPresenter;

    public static final String TAG = "SplashActivity";

    Handler handler;
    Runnable runnable;

    public static Intent getIntent(final Context context) {

        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        runnable = () -> mSplashPresenter.checkLoggedInAndNavigate();
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
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
