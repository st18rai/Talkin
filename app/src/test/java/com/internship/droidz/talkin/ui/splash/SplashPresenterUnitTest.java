package com.internship.droidz.talkin.ui.splash;

import com.internship.droidz.talkin.model.SplashModel;
import com.internship.droidz.talkin.presentation.presenter.splash.SplashPresenter;
import com.internship.droidz.talkin.presentation.view.splash.SplashView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Koroqe on 19-Jan-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterUnitTest {


    @Mock
    SplashModel model;

    @Mock
    SplashView view;

    private SplashPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new SplashPresenter();
    }

    @Test
    public void navigateToLoginScreenShouldBeCalledIfNotLoggedIn() {

        boolean loggedIn = true;
        when(model.isLoggedIn()).thenReturn(false);

        presenter.checkLoggedInAndNavigate();
        verify(view, times(1)).navigateToLoginScreen();
    }

    @Test
    public void navigateToMainScreenShouldBeCalledIfLoggedIn() {

        boolean loggedIn = true;
        when(model.isLoggedIn()).thenReturn(true);

        presenter.checkLoggedInAndNavigate();
        verify(view, times(1)).navigateToMainScreen();
    }
}
