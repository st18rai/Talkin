package com.internship.droidz.talkin.presentation.presenter.registration;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPrefence;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.AmazonConstants;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.model.RegistrationModel;
import com.internship.droidz.talkin.presentation.view.registration.RegistrationView;
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

@InjectViewState
public class RegistrationPresenter extends MvpPresenter<RegistrationView> implements RegistrationModel.RegistrationModelListener {
    private String TAG = "RegistrationPresenter";

    RegistrationModel model;
    RegistrationView view = getViewState();
    Context context;
    CacheSharedPrefence cache = CacheSharedPrefence.getInstance(App.getApp().getApplicationContext());

    private Validator mValidator = new Validator();

    public RegistrationPresenter(RegistrationModel model, RegistrationView view) {

        this.model = model;
        this.view = view;
        this.context = context;
    }

    public void signUp(String email, String password, String fullName, String phone, String website) {
        SessionRepository sessionRepository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        ContentRepository contentRepository = new ContentRepository(ApiRetrofit.getRetrofitApi());
        sessionRepository.signUp(email, password, fullName, phone, website)
                .flatMap(new Func1<SessionModel, Observable<Response<Void>>>() {
                    @Override
                    public Observable<Response<Void>> call(SessionModel sessionModel) {
                        cache.putToken(sessionModel.getSession().getToken());
                        cache.putUserId(Long.valueOf(sessionModel.getSession().getUser_id()));
                        return contentRepository.uploadFile(AmazonConstants.CONTENT_TYPE_JPEG,
                                model.userPicFile, cache.CURRENT_AVATAR);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Void>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("victory", "user created, ava uploaded and updated");
                        view.navigateToMainScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit registration,", ((HttpException) e).response().errorBody().string());
                                //   view.showLoginError();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            Log.i("error_reg_user", "error: " + e.getMessage());
                            // view.showNetworkError();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {

                    }
                });
    }

    public void createAuthSession(String email, String password, SessionModel sessionModel) {

    }

    public void uploadPhoto(Uri photoUri, String email, String password) {

    }

    public void checkPasswordStrength(String password) {

    }

    public void uploadUserPic() {

    }

    public Intent getCameraPictureIntent(PackageManager packageManager) {

        return model.getCameraPictureIntent(packageManager);
    }

    public void addPicToGallery() {

        if (model.currentPhotoPath != null) {
            view.sendBroadcast(model.getMediaScanIntent());
        } else {
            Log.i(TAG, "File doesn't exist");
        }
    }

    public void setupUserPicFromCamera() {

        addPicToGallery();
        setUserPicToModel(model.userPicFileUri);
    }

    public void setupUserPicFromGallery(Intent intent) {

        setUserPicToModel(intent.getData());
    }

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

    public void setFormatWatcher() {

        view.setPhoneMask(model.getFormatWatcher());
    }

    public boolean shouldAskPermission() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void createSession(String email, String password) {

    }

    public void linkFacebook(LoginButton linkFacebookButtonReg) {

        model.linkFacebook(linkFacebookButtonReg, this);
    }

    public void setUserPicFile(Uri uri) {

        model.userPicFileUri = uri;
    }

    public String getCurrentPhotoPath() {

        return model.currentPhotoPath;
    }

    public Uri getUserPicFileUri() {

        return model.userPicFileUri;
    }

    public void onFacebookLogin() {

        view.changeTextFacebookLoginButton();
    }
}
