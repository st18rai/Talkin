package com.internship.droidz.talkin.mvp.splash;

/**
 * Created by Novak Alexandr on 18.01.2017.
 */

public interface SplashContract {

    interface SplashModel {

        boolean isLoggedIn();

        void setLoggedIn(boolean value);
    }

    interface SplashPresenter {

        boolean checkLoggedIn();

        void onResume();

        void onPause();

        void setLoggedIn(boolean value);

        void checkLoggedInAndNavigate();
    }

    interface SplashView {

        void navigateToMainScreen();

        void navigateToLoginScreen();
    }

}
