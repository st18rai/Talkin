package com.internship.droidz.talkin.presentation.presenter.login;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.model.LoginModel;
import com.internship.droidz.talkin.presentation.view.login.LoginView;
import com.internship.droidz.talkin.repository.SessionRepository;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    LoginModel mModel;

    public LoginPresenter() {

        mModel = new LoginModel();
    }

    public void signIn(String email, String password) {

        SessionRepository repository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        repository.signIn(email, password, getViewState());
    }
}
