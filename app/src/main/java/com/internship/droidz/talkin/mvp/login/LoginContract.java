package com.internship.droidz.talkin.mvp.login;

import android.content.Context;

/**
 * Created by Joyker on 19.01.2017.
 */

public interface LoginContract {

    interface LoginModel {

    }

    interface LoginPresenter {
        void checkAndStartTimer(Context context);
        void stopTimer(Context context);
    }

    interface LoginView {
        void signInButtonState();
        void forgotPassword();
        void navigateToRegistrationScreen();
        void navigationToMainScreen();
    }

}
