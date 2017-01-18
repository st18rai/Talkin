package com.internship.droidz.talkin.mvp.splash;

import android.content.Context;

/**
 * Created by Joyker on 18.01.2017.
 */

public interface SplashContract {

    interface SplashModel {
        boolean isLoggedIn();
        void setLoggedIn(boolean value);
    }

    interface SplashPresenter {
        void navigateToMain();
        void navigateToLogin();
        boolean checkLoggedIn();
        void setLoggedIn(boolean value);

    }


    interface SplashView {

    }

}
