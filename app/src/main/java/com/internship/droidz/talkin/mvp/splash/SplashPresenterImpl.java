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
    SplashContract.SplashModel model ;

    public SplashPresenterImpl(Context ctx)
    {
        this.ctx=ctx;
        model= new SplashModelImpl(ctx);
    }

    @Override
    public boolean checkLoggedIn() {
        return model.isLoggedIn();
    }

    @Override
    public void setLoggedIn(boolean value) {
        model.setLoggedIn(value);
    }
}
