package com.internship.droidz.talkin.presentation.presenter.login;


import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.model.LoginModel;
import com.internship.droidz.talkin.presentation.view.login.LoginView;
import com.internship.droidz.talkin.repository.SessionRepository;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    private LoginModel model = new LoginModel();

    public void signIn(String email, String password, SessionRepository repository) {
        getViewState().showLoading();
        repository.signIn(email, password, getViewState());
    }

    public void disableButton() {
        getViewState().disableButton();
    }

    public void disableButtonIfEmailEmpty(String email) {
        if (TextUtils.isEmpty(email))
            getViewState().disableButton();
    }

    public void enableButtonIfEmailEntered(String email) {
        if (TextUtils.isEmpty(email))
            getViewState().disableButton();
        else
            getViewState().enableButton();
    }

    public void navigateToRegistrationScreen() {
        getViewState().navigateToRegistrationScreen();
    }

    public void showForgotPasswordDialog() {
        getViewState().forgotPassword();
    }

    public void checkAndStartTimer() {
        model.checkAndStartTimer();
    }

    public void stopTimer() {
        model.stopTimer();
    }
}
