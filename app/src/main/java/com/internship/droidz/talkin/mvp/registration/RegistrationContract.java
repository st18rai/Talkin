package com.internship.droidz.talkin.mvp.registration;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.data.model.SessionModel;

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

        interface RegistrationModelListener {

            void onFacebookLogin();

        }

        File createImageFile() throws IOException;

        FormatWatcher getFormatWatcher();

        void linkFacebook(LoginButton linkFacebookButtonReg, RegistrationModelListener listener);

    }

    interface RegistrationPresenter {

        Intent getCameraPictureIntent(PackageManager packageManager);

        void setupUserPicFromCamera();

        void setupUserPicFromGallery(Intent intent);

        void setUserPicToModel(Uri uri);

        void setFormatWatcher();

        void addPicToGallery();

        boolean shouldAskPermission();

        void createSession(String email, String password);

        void signUp(String email,String password, String fullName, String phone, String website);

        void createAuthSession(String email, String password, SessionModel sessionModel);

        void uploadUserPic();

        void linkFacebook(LoginButton linkFacebookButtonReg);

        void setUserPicFile(Uri uri);

        String getCurrentPhotoPath();

        Uri getUserPicFileUri();
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

        void sendBroadcast(Intent mediaScanIntent);
    }
}
