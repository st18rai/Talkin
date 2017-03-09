package com.internship.droidz.talkin.presentation.view.login;

import android.widget.Button;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface LoginView extends MvpView {

    void signInButtonState();

    void showLoginError();

    void showNetworkError();

    void disableButton(Button button);

    void enableButton(Button button);

    void forgotPassword();

    void navigateToRegistrationScreen();

    void navigationToMainScreen();
}
