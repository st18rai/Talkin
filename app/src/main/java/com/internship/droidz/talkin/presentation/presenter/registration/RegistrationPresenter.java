package com.internship.droidz.talkin.presentation.presenter.registration;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.model.RegistrationModel;
import com.internship.droidz.talkin.presentation.view.registration.RegistrationView;
import com.internship.droidz.talkin.utils.Validator;

import java.io.File;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> implements RegistrationListener {

    private String TAG = "RegistrationPresenter";

    RegistrationModel mModel;
    RegistrationView mView;

    public RegistrationPresenter() {

        mModel = new RegistrationModel();
        mView = getViewState();
    }

    public Intent getCameraPictureIntent(PackageManager packageManager) {

        return mModel.getCameraPictureIntent(packageManager);
    }

    public void setupUserPicFromCamera() {

        mModel.addPicToGallery();
        if (Validator.checkUserPicSize(mModel.getmUserPicFile())) {
            try {
                mView.setImageUriToView(mModel.getUserPicFileUri());
            } catch (Exception e) {
                e.printStackTrace();
                mView.showAlertFailedToLoad();
            }
        } else {
            mView.showAlertMaxSizeOfImage();
            mModel.setmUserPicFile(null);
            mModel.setUserPicFileUri(null);
        }
    }

    public void setupUserPicFromGallery(Intent intent) {

        try {
            mModel.setUserPic(intent.getData());
            mView.setImageUriToView(Uri.fromFile(mModel.getmUserPicFile()));
        } catch (NullPointerException e) {
            e.printStackTrace();
            mView.showAlertFailedToLoad();
        } catch (Exception e) {
            e.printStackTrace();
            mView.showAlertMaxSizeOfImage();
        }
    }

    public File getUserPicFile() {

        return mModel.getmUserPicFile();
    }

    public void setFormatWatcher() {

        mView.setPhoneMask(mModel.getFormatWatcher());
    }

    public boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void signUp(String email, String password, String fullName, String phone, String website) {

        mModel.signUp(this, email, password, fullName, phone, website);
    }

    public void linkFacebook(LoginButton linkFacebookButtonReg) {

        mModel.linkFacebook(linkFacebookButtonReg, this);
    }

    public void onFacebookLogin() {

        mView.changeTextFacebookLoginButton();
    }

    public void onRegistrationCompleted() {

        mView.navigateToMainScreen();
    }

    public void onRegistrationError() {

        mView.showRegistrationError();
    }

    public void onNetworkError() {

        mView.showNetworkError();
    }

    public void setOnActivityResultFacebookManager(int requestCode, int resultCode, Intent returnedData) {

        mModel.setOnActivityResultFacebookManager(requestCode, resultCode, returnedData);
    }
}