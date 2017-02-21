package com.internship.droidz.talkin.presentation.view.registration;

import android.content.Intent;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;

import ru.tinkoff.decoro.watchers.FormatWatcher;

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

    void comparePasswords();

    void navigateToMainScreen();

    void askPermissionWriteExternal();

    void changeTextFacebookLoginButton();

    void sendBroadcast(Intent mediaScanIntent);


    void activitySendBroadcast(Intent mediaScanIntent);

    void showInvalidRegistrationDataError();
}
