package com.internship.droidz.talkin.presentation.presenter.splash;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.model.SplashModel;
import com.internship.droidz.talkin.presentation.view.splash.SplashView;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {

    private SplashModel mModel = new SplashModel();

    public boolean checkLoggedIn() {
        return mModel.isLoggedIn();
    }

    public void setLoggedIn(boolean value) {
        mModel.setLoggedIn(value);
    }

    public void checkLoggedInAndNavigate() {

        if (checkLoggedIn()) {
            getViewState().navigateToMainScreen();
        } else {
            getViewState().navigateToLoginScreen();
        }
    }
}
