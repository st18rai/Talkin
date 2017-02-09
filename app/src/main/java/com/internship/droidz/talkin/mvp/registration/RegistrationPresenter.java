package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPrefence;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.AmazonConstants;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.repository.ContentRepository;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.utils.Validator;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by st18r on 20.01.2017.
 */


public class RegistrationPresenter implements
        RegistrationContract.RegistrationPresenter,
        RegistrationContract.RegistrationModel.RegistrationModelListener {

    private String TAG = "RegistrationPresenter";

    RegistrationModel model;
    RegistrationContract.RegistrationView view;
    Context context;
    CacheSharedPrefence cache = CacheSharedPrefence.getInstance(App.getApp().getApplicationContext());

    private Validator mValidator = new Validator();

    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view) {

        this.model = model;
        this.view = view;
        this.context = context;
    }

    @Override
    public void signUp(String email, String password, String fullName, String phone, String website) {
        SessionRepository sessionRepository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        ContentRepository contentRepository  = new ContentRepository(ApiRetrofit.getRetrofitApi());
        sessionRepository.signUp(email,password,fullName,phone,website)
                .flatMap(new Func1<SessionModel, Observable<Response<Void>>>() {
                    @Override
                    public Observable<Response<Void>> call(SessionModel sessionModel) {
                        cache.putToken(sessionModel.getSession().getToken());
                        cache.putUserId(Long.valueOf(sessionModel.getSession().getUser_id()));
                       return contentRepository.uploadFile(AmazonConstants.CONTENT_TYPE_JPEG,
                               model.userPicFile,cache.CURRENT_AVATAR);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Void>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("victory","user created, ava uploaded and updated");
                        view.navigateToMainScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit registration,",((HttpException) e).response().errorBody().string());
                             //   view.showLoginError();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            Log.i("error_reg_user","error: "+e.getMessage());
                           // view.showNetworkError();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {

                    }
                });
    }

    @Override
    public void uploadPhoto(Uri photoUri, String email, String password) {

    }

    @Override
    public void checkPasswordStrength(String password) {

    }

    @Override
    public void uploadUserPic() {

    }

    @Override
    public Intent getCameraPictureIntent(PackageManager packageManager) {

        return model.getCameraPictureIntent(packageManager);
    }

    @Override
    public void addPicToGallery() {

        if (model.currentPhotoPath != null) {
            view.sendBroadcast(model.getMediaScanIntent());
        } else {
            Log.i(TAG, "File doesn't exist");
        }
    }

    @Override
    public void setupUserPicFromCamera() {

        addPicToGallery();
        setUserPicToModel(model.userPicFileUri);
    }

    @Override
    public void setupUserPicFromGallery(Intent intent) {

        setUserPicToModel(intent.getData());
    }

    @Override
    public void setUserPicToModel(Uri uri) {

        if (mValidator.checkUserPicSize(uri)) {
            try {
                model.setUserPic(uri);
                view.setImageUriToView(uri);
            } catch (Exception e) {
                view.showAlertFailedToLoad();
            }
        } else {
            view.showAlertMaxSizeOfImage();
            model.userPicFile = null;
            model.userPicFileUri = null;
        }
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
    public void linkFacebook(LoginButton linkFacebookButtonReg) {

        model.linkFacebook(linkFacebookButtonReg, this);
    }

    @Override
    public void setUserPicFile(Uri uri) {

        model.userPicFileUri = uri;
    }

    @Override
    public String getCurrentPhotoPath() {

        return model.currentPhotoPath;
    }

    @Override
    public Uri getUserPicFileUri() {

        return model.userPicFileUri;
    }

    @Override
    public void onFacebookLogin() {

        view.changeTextFacebookLoginButton();
    }
}
