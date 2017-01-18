package com.internship.droidz.talkin.mvp.splash;

import android.content.Context;
import android.content.Intent;

import com.internship.droidz.talkin.mvp.login.LoginScreen;
import com.internship.droidz.talkin.mvp.main.MainScreen;

/**
 * Created by Joyker on 18.01.2017.
 */

public class SplashPresenterImpl implements SplashContract.SplashPresenter {
    Context ctx;
    SplashModelImpl model ;

    public SplashPresenterImpl(Context ctx)
    {
        this.ctx=ctx;
        model= new SplashModelImpl(ctx);
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent(ctx, MainScreen.class);
        ctx.startActivity(intent);
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(ctx, LoginScreen.class);
        ctx.startActivity(intent);
    }

    @Override
    public boolean checkLiggedIn() {
        return model.isLoggedIn();
    }

    @Override
    public void setLoggedIn(boolean value) {
        model.setLoggedIn(value);
    }
}
