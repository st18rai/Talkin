package com.internship.droidz.talkin.presentation.presenter.registration;

/**
 * Created by Koroqe on 12-Feb-17.
 */

public interface RegistrationListener {

    void onFacebookLogin();

    void onRegistrationCompleted();

    void onRegistrationError();

    void onNetworkError();
}
