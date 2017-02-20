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

    // TODO: 2/20/17 [Code Review] this method does not sound as a method of View layer
    void comparePasswords();

    void navigateToMainScreen();

    void askPermissionWriteExternal();

    // TODO: 2/20/17 [Code Review] not obvious how exactly this text should be changed. Randomly maybe? :)
    void changeTextFacebookLoginButton();

    void sendBroadcast(Intent mediaScanIntent);


    void activitySendBroadcast(Intent mediaScanIntent);
}
