package com.internship.droidz.talkin.presentation.presenter.forgotPassword;


import com.internship.droidz.talkin.presentation.view.forgotPassword.ForgotPasswordView;
import com.internship.droidz.talkin.utils.Validator;

public class ForgotPasswordPresenter {

    ForgotPasswordView forgotPasswordView;

    public ForgotPasswordPresenter(ForgotPasswordView view){
        this.forgotPasswordView = view;
    }

    public void enablePositiveButton() {
        forgotPasswordView.enablePositiveButton();
    }

    public void disablePositiveButton() {
        forgotPasswordView.disablePositiveButton();
    }

    public void setPositiveButtonState(String email) {
        if (!Validator.isValidEmail(email)) {
            forgotPasswordView.disablePositiveButton();
        } else {
            forgotPasswordView.enablePositiveButton();
        }
    }
}
