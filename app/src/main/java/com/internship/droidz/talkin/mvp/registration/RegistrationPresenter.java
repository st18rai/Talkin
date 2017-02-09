package com.internship.droidz.talkin.mvp.registration;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.facebook.login.widget.LoginButton;
import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPrefence;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.model.UserModel;
import com.internship.droidz.talkin.data.web.AmazonConstants;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.RegistrationRequest;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.UserRequestModel;
import com.internship.droidz.talkin.data.web.requests.UserSignUpRequest;
import com.internship.droidz.talkin.repository.ContentRepository;
import com.internship.droidz.talkin.utils.Validator;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
    CacheSharedPrefence cache = CacheSharedPrefence.getInstance(App.getApp().getApplicationContext());

    private Validator mValidator = new Validator();

    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view) {

        this.model = model;
        this.view = view;
    }

    @Override
    public void signUp(String email, String password, String fullName, String phone, String website) {

        int nonce= WebUtils.getNonce();
        long timestamp = System.currentTimeMillis()/1000l;
        ApiRetrofit.getRetrofitApi().getUserService()
                .getSession(new SessionRequest(ApiRetrofit.APP_ID,ApiRetrofit.APP_AUTH_KEY,
                        String.valueOf(nonce),String.valueOf(timestamp),WebUtils.calcSignature(nonce,timestamp)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SessionModel>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.i("error reg", "msg: "+e.getMessage());
                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {

                        UserSignUpRequest requestReg = new UserSignUpRequest(email,
                                password,fullName,phone,website);
                        RegistrationRequest request = new RegistrationRequest(requestReg);
                        ApiRetrofit.getRetrofitApi().getUserService().requestSignUp(request,sessionModel.getSession().getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<UserModel>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.i("registration","registered");
                                        createSession(email,password);
                                        view.navigateToMainScreen();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.i("registration","failed :( "+ e.getMessage());
                                    }

                                    @Override
                                    public void onNext(UserModel userModel) {
                                        Log.i("user_id",userModel.getUser().getId().toString());
                                    }
                                });
                    }
                });
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
    public void createSession(String email, String password) {

        int nonce= WebUtils.getNonce();
        long timestamp = System.currentTimeMillis()/1000l;
        ApiRetrofit.getRetrofitApi().getUserService()
                .getSession(new SessionRequest(ApiRetrofit.APP_ID,ApiRetrofit.APP_AUTH_KEY,
                        String.valueOf(nonce),String.valueOf(timestamp),WebUtils.calcSignature(nonce,timestamp)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //  .subscribe(sessionModel -> {})
                .subscribe(new Subscriber<SessionModel>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.i("error","message: "+e.getMessage());
                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit error,",((HttpException) e).response().errorBody().string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {
                        createAuthSession(email, password, sessionModel);
                    }
                });
    }

    @Override
    public void createAuthSession(String email, String password, SessionModel sessionModel) {

        int nonce= WebUtils.getNonce();
        long timestamp = System.currentTimeMillis()/1000l;
        SessionWithAuthRequest request =  new SessionWithAuthRequest(
                new UserRequestModel(email, password),
                ApiRetrofit.APP_ID,
                ApiRetrofit.APP_AUTH_KEY,
                String.valueOf(nonce),
                String.valueOf(timestamp),
                WebUtils.calcSignature(nonce, timestamp,
                        email,
                        password));

        ApiRetrofit.getRetrofitApi().getUserService()
                .getSessionWithAuth(request,sessionModel.getSession().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SessionModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("debug","logged in");
//                        if (model.userPicFile != null) {
                            uploadUserPic();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit error,",((HttpException) e).response().errorBody().string());
                                //   Toast.makeText((LoginScreen)view,"Wrong login or password",Toast.LENGTH_LONG).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else Log.i("error","some error");
                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {
                        Log.i("user",String.valueOf(sessionModel.getSession().getUser_id()));
                        cache.putToken(String.valueOf(sessionModel.getSession().getToken()));
                    }
                });
    }

    @Override
    public void uploadUserPic() {

        ContentRepository repo = new ContentRepository(ApiRetrofit.getRetrofitApi());
        repo.uploadFile(AmazonConstants.CONTENT_TYPE_JPEG, model.userPicFile, "AVATAR")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Void>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("userPic", "File uploaded");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("error","message: " + e.getMessage());
                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit error photo,",((HttpException) e).response().errorBody().string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            Log.i("error photo",""+e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {}
                });
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
