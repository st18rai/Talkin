package com.internship.droidz.talkin.mvp.login;

/**
 * Created by Joyker on 19.01.2017.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    LoginContract.LoginModel model;
    LoginContract.LoginView view;

    public LoginPresenterImpl (LoginContract.LoginModel model, LoginContract.LoginView view){
        this.model = model;
        this.view = view;
    }

}
