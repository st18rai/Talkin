package com.internship.droidz.talkin.mvp.registration;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.utils.Validator;

import java.io.File;

/**
 * Created by st18r on 20.01.2017.
 */


public class RegistrationPresenter implements
        RegistrationContract.RegistrationPresenter,
        RegistrationContract.RegistrationModel.RegistrationModelListener {

    private String TAG = "RegistrationPresenter";

    RegistrationModel model;
    RegistrationContract.RegistrationView view;

    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view) {

        this.model = model;
        this.view = view;
    }

    @Override
    public Intent getCameraPictureIntent(PackageManager packageManager) {

        return model.getCameraPictureIntent(packageManager);
    }

    @Override
    public void addPicToGallery() {

        if (model.getmUserPicFile() != null) {
            view.activitySendBroadcast(model.getMediaScanIntent());
        } else {
            Log.i(TAG, "File doesn't exist");
        }
    }

    @Override
    public void setupUserPicFromCamera() {

        addPicToGallery();
        if (Validator.checkUserPicSize(model.getmUserPicFile())) {
            try {
                view.setImageUriToView(model.getUserPicFileUri());
            } catch (Exception e) {
                e.printStackTrace();
                view.showAlertFailedToLoad();
            }
        } else {
            view.showAlertMaxSizeOfImage();
            model.setmUserPicFile(null);
            model.setUserPicFileUri(null);
        }
    }

    @Override
    public void setupUserPicFromGallery(Intent intent) {

        try {
            model.setUserPic(intent.getData());
            view.setImageUriToView(Uri.fromFile(model.getmUserPicFile()));
        } catch (NullPointerException e) {
            e.printStackTrace();
            view.showAlertFailedToLoad();
        } catch (Exception e) {
            e.printStackTrace();
            view.showAlertMaxSizeOfImage();
        }
    }

    @Override
    public File getUserPicFile() {

        return model.getmUserPicFile();
    }

    @Override
    public void setFormatWatcher() {

        view.setPhoneMask(model.getFormatWatcher());
    }

    @Override
    public boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void signUp(String email, String password, String fullName, String phone, String website) {

        model.signUp(this, email, password, fullName, phone, website);
    }

    @Override
    public void linkFacebook(LoginButton linkFacebookButtonReg) {

        model.linkFacebook(linkFacebookButtonReg, this);
    }

    @Override
    public void onFacebookLogin() {

        view.changeTextFacebookLoginButton();
    }

    @Override
    public void onRegistrationCompleted() {

        view.navigateToMainScreen();
    }

    @Override
    public void onRegistrationError() {

        view.showRegistrationError();
    }

    @Override
    public void onNetworkError() {

        view.showNetworkError();
    }
}
