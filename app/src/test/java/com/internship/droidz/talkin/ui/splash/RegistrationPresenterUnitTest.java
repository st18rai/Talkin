package com.internship.droidz.talkin.ui.splash;

import com.internship.droidz.talkin.model.RegistrationModel;
import com.internship.droidz.talkin.presentation.presenter.registration.RegistrationPresenter;
import com.internship.droidz.talkin.presentation.view.registration.RegistrationView;
import com.internship.droidz.talkin.repository.SessionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Koroqe on 13-Feb-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterUnitTest {

    @Mock
    RegistrationModel model;

    @Mock
    RegistrationView view;

    @Mock
    RegistrationPresenter presenter;

    @Mock
    SessionRepository sessionRepository;






    @Test
    public void checkSignUpWithPhoto() {


    }

    @Test
    public void settingImageToViewShouldBeCalledIfCameraCapture() {


    }




}
