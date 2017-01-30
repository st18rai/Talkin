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

    }

    interface RegistrationPresenter {
        File createImageFile(Context context) throws IOException;
        Intent getCameraPictureIntent(PackageManager packageManager, Context context);
        void checkImageAndSetToView();
        void setUserPicUri(Uri uri);
        void setFormatWatcher();
        void addPicToGallery(Context context);
    }


    interface RegistrationView {
        void showAlertMaxSizeOfImage();
        void showAlertFailedToLoad();
        void setImageUriToView(Uri uri);
        void setPhoneMask(FormatWatcher formatWatcher);
    }
}
