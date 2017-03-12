package com.internship.droidz.talkin.presentation.view.login;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface LoginView extends MvpView {

    void signInButtonState();

    void showLoginError();

    void showNetworkError();

    void showLoading();

    void hideLoading();

    void disableButton();

    void enableButton();

    void forgotPassword();

    void navigateToRegistrationScreen();

    void navigateToMainScreen();
}
