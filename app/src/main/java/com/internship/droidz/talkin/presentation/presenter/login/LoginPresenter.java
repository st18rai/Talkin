package com.internship.droidz.talkin.presentation.presenter.login;


import android.text.TextUtils;
import android.widget.Button;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.model.LoginModel;
import com.internship.droidz.talkin.presentation.view.login.LoginView;
import com.internship.droidz.talkin.repository.SessionRepository;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    LoginModel model = new LoginModel();

    public void signIn(String email, String password, SessionRepository repository) {
        repository.signIn(email, password, getViewState());
    }

    public void disableButton(Button button) {
        getViewState().disableButton(button);
    }

    public void enableButton(Button button) {
        getViewState().enableButton(button);
    }

    public void disableButtonIfEmailEmpty(String email, Button button) {
        if (TextUtils.isEmpty(email))
            getViewState().disableButton(button);
    }

    public void enableButtonIfEmailEntered(String email, Button button) {
        if (TextUtils.isEmpty(email))
            getViewState().disableButton(button);
        else
            getViewState().enableButton(button);
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
