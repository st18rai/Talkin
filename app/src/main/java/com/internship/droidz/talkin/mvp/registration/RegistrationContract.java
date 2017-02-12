package com.internship.droidz.talkin.mvp.registration;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.facebook.login.widget.LoginButton;

import java.io.File;
import java.io.IOException;

import ru.tinkoff.decoro.watchers.FormatWatcher;

/**
 * Created by st18r on 20.01.2017.
 */

public interface RegistrationContract {

    interface RegistrationModel {

        Intent getMediaScanIntent();

        void grantAllPermissionsToUserPicFile(Intent intent);

        void setUserPic(Uri uri);

        Intent getCameraPictureIntent(PackageManager packageManager);

        File getmUserPicFile();

        Uri getUserPicFileUri();

        void setUserPicFileUri(Uri userPicFileUri);

        void setmUserPicFile(File mUserPicFile);

        interface RegistrationModelListener {

            void onFacebookLogin();

            void onRegistrationCompleted();

            void onRegistrationError();

            void onNetworkError();
        }

        File createImageFile() throws IOException;

        FormatWatcher getFormatWatcher();

        void signUp(RegistrationModelListener listener, String email, String password, String fullName, String phone, String website);

        void linkFacebook(LoginButton linkFacebookButtonReg, RegistrationModelListener listener);

    }

    interface RegistrationPresenter {

        Intent getCameraPictureIntent(PackageManager packageManager);

        void setupUserPicFromCamera();

        void setupUserPicFromGallery(Intent intent);

        File getUserPicFile();

        void setFormatWatcher();

        void addPicToGallery();

        boolean shouldAskPermission();

        void signUp(String email, String password, String fullName, String phone, String website);

        void linkFacebook(LoginButton linkFacebookButtonReg);

    }

    interface RegistrationView {

        void showProgress();

        void hideProgress();

        void startCameraForCapture();

        void startGalleryForCapture();

        void showAlertMaxSizeOfImage();

        void showAlertFailedToLoad();

        void showDialogChooseSource();

        void setImageUriToView(Uri uri);

        void setPhoneMask(FormatWatcher formatWatcher);

        void checkEmail();

        void checkPassword();

        void comparePasswords();

        void navigateToMainScreen();

        void askPermissionWriteExternal();

        void changeTextFacebookLoginButton();

        void activitySendBroadcast(Intent mediaScanIntent);

        void showRegistrationError();

        void showNetworkError();
    }
}
