package com.internship.droidz.talkin.mvp.splash;

import android.content.Context;

/**
 * Created by Joyker on 18.01.2017.
 */

public class SplashPresenterImpl implements SplashContract.SplashPresenter {
    SplashContract.SplashModel model;
    SplashContract.SplashView view;

    public SplashPresenterImpl(SplashContract.SplashModel model, SplashContract.SplashView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public boolean checkLoggedIn() {
        return model.isLoggedIn();
    }

    @Override
    public void setLoggedIn(boolean value) {
        model.setLoggedIn(value);
    }

    @Override
    public void checkLoggedInAndNavigate() {
        if (checkLoggedIn()) {
            view.navigateToMainScreen();
        }
        else {
            view.navigateToLoginScreen();
        }
    }

}
