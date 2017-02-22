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

        // TODO: 2/20/17 [Code Review] pass this instance as parameter, do not create it here, you
        // will not be able to mock it for tests
        SessionRepository repository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        repository.signIn(email, password, getViewState());
    }
}
