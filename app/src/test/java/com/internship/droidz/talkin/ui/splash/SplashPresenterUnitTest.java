package com.internship.droidz.talkin.ui.splash;

import com.internship.droidz.talkin.mvp.splash.SplashContract;
import com.internship.droidz.talkin.mvp.splash.SplashPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by Koroqe on 19-Jan-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterUnitTest {

    @Mock
    SplashContract.SplashModel model;

    @Mock
    SplashContract.SplashView view;

    private SplashPresenterImpl splashPresenter;

    @Before
    public void setUp() throws Exception {
        splashPresenter = new SplashPresenterImpl(model, view);
    }

    @Test
    public void navigateToLoginScreenCouldBeCalledIfNotLoggedIn() {

        boolean loggedIn = true;
        when(model.isLoggedIn()).thenReturn(false);

        splashPresenter.checkLoggedInAndNavigate();
        verify(view, times(1)).navigateToLoginScreen();
    }

    @Test
    public void navigateToMainScreenCouldBeCalledIfLoggedIn() {

        boolean loggedIn = true;
        when(model.isLoggedIn()).thenReturn(true);

        splashPresenter.checkLoggedInAndNavigate();
        verify(view, times(1)).navigateToMainScreen();
    }
}
