package com.internship.droidz.talkin.presentation.view.login;

import com.arellomobile.mvp.MvpView;

public interface LoginView extends MvpView {

    void signInButtonState();
    void forgotPassword();
    void navigateToRegistrationScreen();
    void navigationToMainScreen();
    void showLoginError();
    void showNetworkError();

}
