package com.internship.droidz.talkin.mvp.registration;

/**
 * Created by st18r on 20.01.2017.
 */

public class RegistrationPresenterImpl implements RegistrationContract.RegistrationPresenter {

    RegistrationContract.RegistrationModel model;
    RegistrationContract.RegistrationView view;

    public RegistrationPresenterImpl(RegistrationContract.RegistrationModel model, RegistrationContract.RegistrationView view) {
        this.model = model;
        this.view = view;
    }
}
