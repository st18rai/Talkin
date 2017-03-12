package com.internship.droidz.talkin.presentation.view.registration;

import android.content.Intent;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.tinkoff.decoro.watchers.FormatWatcher;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface RegistrationView extends MvpView {

    void showProgress();

    void hideProgress();

    void startCameraForCapture();

    void startGalleryForCapture();

    void showAlertMaxSizeOfImage();

    void showAlertFailedToLoad();

    void showDialogChooseSource();

    void setImageUriToView(Uri uri);

    void setPhoneMask(FormatWatcher formatWatcher);

    void showRegistrationError();

    void showNetworkError();

    void checkEmail();

    void checkPassword();

    // TODO: 2/20/17 [Code Review] this method does not sound as a method of View layer
    void comparePasswords();

    void navigateToMainScreen();

    void askPermissionWriteExternal();

    void setFacebookLoginButtonAsLinked();

    void sendBroadcast(Intent mediaScanIntent);

    void showInvalidRegistrationDataError();
}
