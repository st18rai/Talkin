package com.internship.droidz.talkin.presentation.presenter.splash;


import com.internship.droidz.talkin.model.SplashModel;
import com.internship.droidz.talkin.presentation.view.splash.SplashView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {
    SplashModel model;
    SplashView view = getViewState();

    public SplashPresenter(SplashModel model, SplashView view) {
        this.model = model;
        this.view = view;
    }

    public boolean checkLoggedIn() {
        return model.isLoggedIn();
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void setLoggedIn(boolean value) {
        model.setLoggedIn(value);
    }

    public void checkLoggedInAndNavigate() {
        if (checkLoggedIn()) {
            view.navigateToMainScreen();
        } else {
            view.navigateToLoginScreen();
        }
    }
}
