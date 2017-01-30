package com.internship.droidz.talkin.mvp.registration;

/**
 * Created by st18r on 20.01.2017.
 */

public interface RegistrationContract {

    interface RegistrationModel {

    }

    interface RegistrationPresenter {

    }


    interface RegistrationView {

        void checkEmail();

        void checkPasswordLength();

        void comparePasswords();

    }
}
