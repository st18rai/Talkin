package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

import ru.tinkoff.decoro.watchers.FormatWatcher;

/**
 * Created by st18r on 20.01.2017.
 */

public interface RegistrationContract {

    interface RegistrationModel {

        void getAllPermissionsToUserPicFile(Context context, Intent intent);

        void addPicToGallery(Context context);

        File createImageFile() throws IOException;

        FormatWatcher getFormatWatcher();

    }

    interface RegistrationPresenter {

        Intent getCameraPictureIntent(PackageManager packageManager);

        void setupUserPicFromCamera();

        void setupUserPicFromGallery(Intent intent);

        void checkImageSizeAndSetToView();

        void setUserPicUri(Uri uri);

        void setFormatWatcher();

        void addPicToGallery();

        boolean shouldAskPermission();

        void signUp(String email,String password, String fullName, String phone, String website);

        void uploadPhoto(Uri photoUri, String email, String password);

        void checkPasswordStrength(String password);

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
    }
}
