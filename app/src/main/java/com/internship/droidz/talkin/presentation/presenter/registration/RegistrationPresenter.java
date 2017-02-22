package com.internship.droidz.talkin.presentation.presenter.registration;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPreference;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.AmazonConstants;
import com.internship.droidz.talkin.model.RegistrationModel;
import com.internship.droidz.talkin.presentation.view.registration.RegistrationView;
import com.internship.droidz.talkin.repository.ContentRepository;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.utils.Validator;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private String TAG = "RegistrationPresenter";

    RegistrationModel mModel;
    RegistrationView mView;
    CacheSharedPreference cache = CacheSharedPreference.getInstance(App.getApp().getApplicationContext());

    public RegistrationPresenter() {

        mModel = new RegistrationModel();
        mView = getViewState();
    }

    public Intent getCameraPictureIntent(PackageManager packageManager) {

        return mModel.getCameraPictureIntent(packageManager);
    }

    public void setupUserPicFromCamera() {

        mModel.addPicToGallery();
        if (Validator.checkUserPicSize(mModel.getUserPicFile())) {
            try {
                mView.setImageUriToView(mModel.getUserPicFileUri());
            } catch (Exception e) {
                e.printStackTrace();
                mView.showAlertFailedToLoad();
            }
        } else {
            mView.showAlertMaxSizeOfImage();
            mModel.setUserPicFile(null);
            mModel.setUserPicFileUri(null);
        }
    }

    public void setupUserPicFromGallery(Intent intent) {

        try {
            mModel.setUserPic(intent.getData());
            mView.setImageUriToView(Uri.fromFile(mModel.getUserPicFile()));
        } catch (NullPointerException e) {
            e.printStackTrace();
            mView.showAlertFailedToLoad();
        } catch (Exception e) {
            e.printStackTrace();
            mView.showAlertMaxSizeOfImage();
        }
    }

    public File getUserPicFile() {

        return mModel.getUserPicFile();
    }

    public void setFormatWatcher() {

        mView.setPhoneMask(mModel.getFormatWatcher());
    }

    public boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void signUp(SessionRepository sessionRepository, ContentRepository contentRepository,
                       String email, String password, String fullName, String phone, String website) {

        if (Validator.validateRegistrationData(email, password, fullName, phone, website)) {
            if(mModel.getUserPicFile()==null)
            {
                signUpWithoutPhoto(sessionRepository, email, password, fullName, phone, website);
            }
            else
            {
                signUpWithPhoto(sessionRepository, contentRepository, email, password, fullName, phone, website);
            }
        } else {
            mView.showInvalidRegistrationDataError();
        }
    }

    private void signUpWithPhoto(SessionRepository sessionRepository, ContentRepository contentRepository,
                                 String email, String password, String fullName, String phone, String website) {

        sessionRepository.signUp(email,password,fullName,phone,website)
                .flatMap(new Func1<SessionModel, Observable<Response<Void>>>() {
                    @Override
                    public Observable<Response<Void>> call(SessionModel sessionModel) {
                        cache.putToken(sessionModel.getSession().getToken());
                        cache.putUserId(Long.valueOf(sessionModel.getSession().getUser_id()));
                        return contentRepository.uploadFile(AmazonConstants.CONTENT_TYPE_JPEG,
                                mModel.getUserPicFile(), cache.CURRENT_AVATAR);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Void>>() {

                    @Override
                    public void onCompleted() {

                        Log.i("victory","user created, ava uploaded and updated");
                        onRegistrationCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit registration,",((HttpException) e).response().errorBody().string());
                                onRegistrationError();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            Log.i("error_reg_user","error: "+e.getMessage());
                            onNetworkError();
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onNext(Response<Void> voidResponse) {

                    }
                });
    }

    private void signUpWithoutPhoto(SessionRepository sessionRepository,
                                    String email, String password, String fullName, String phone, String website) {

        sessionRepository.signUp(email,password,fullName,phone,website)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SessionModel>() {

                    @Override
                    public void onCompleted() {

                        Log.i("victory","user created, without ava");
                        onRegistrationCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit registration,",((HttpException) e).response().errorBody().string());
                                onRegistrationError();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            Log.i("error_reg_user","error: "+e.getMessage());
                            onNetworkError();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {
                        cache.putToken(sessionModel.getSession().getToken());
                        cache.putUserId(Long.valueOf(sessionModel.getSession().getUser_id()));
                    }
                });
    }

    public void linkFacebook(LoginButton linkFacebookButtonReg) {

        CallbackManager callbackManager= CallbackManager.Factory.create();
        linkFacebookButtonReg.performClick();
        linkFacebookButtonReg.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i(TAG, "Facebook onSuccess: " + accessToken);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            response.toString();
                            try {
                                mModel.setFacebookUserID(object.getString("id"));
                                Log.i(TAG, "Facebook linked: " + mModel.getFacebookUserID());
                                onFacebookLogin();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "Facebook link cancelled");
            }

            @Override
            public void onError(FacebookException e) {

                Log.i(TAG, "Facebook link error");
                e.printStackTrace();
            }
        });
    }

    public void onFacebookLogin() {

        mView.setFacebookLoginButtonAsLinked();
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

}