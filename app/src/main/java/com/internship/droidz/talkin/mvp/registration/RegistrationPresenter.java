package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPrefence;
import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.AmazonConstants;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.RegistrationRequest;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.UserRequestModel;
import com.internship.droidz.talkin.data.web.requests.UserSignUpRequest;
import com.internship.droidz.talkin.repository.ContentRepository;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.utils.Validator;

import java.io.File;
import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by st18r on 20.01.2017.
 */


public class RegistrationPresenter implements RegistrationContract.RegistrationPresenter {

    private String TAG = "RegistrationPresenter";

    RegistrationModel model;
    RegistrationContract.RegistrationView view;
    Context context;
    CacheSharedPrefence cache = CacheSharedPrefence.getInstance(App.getApp().getApplicationContext());

    private Validator validator = new Validator();

    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view, Context context) {
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            try
                            {
                                Log.i("retrofit registration,",((HttpException) e).response().errorBody().string());
                             //   view.showLoginError();

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else
                        {
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
    public void checkPasswordStrength(String password) {

    }

    @Override
    public Intent getCameraPictureIntent(PackageManager packageManager) {

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(packageManager) != null) {
            try {
                model.userPicFile = model.createImageFile();
            } catch (IOException e) {
                Log.i(TAG, "Can't create file!", e);
            }
            if (model.userPicFile != null) {
                model.userPicFileUri = FileProvider.getUriForFile(context,
                        "com.internship.droidz.talkin.fileprovider",
                        model.userPicFile);
                model.getAllPermissionsToUserPicFile(context, pictureIntent);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, model.userPicFileUri);
            }
        }
        return pictureIntent;
    }

    @Override
    public void addPicToGallery() {
        model.addPicToGallery(context);
    }

    @Override
    public void setupUserPicFromCamera() {
        addPicToGallery();
        checkImageSizeAndSetToView();
    }

    @Override
    public void setupUserPicFromGallery(Intent intent) {
        setUserPicToModel(intent.getData());
        checkImageSizeAndSetToView();
    }

    @Override
    public void checkImageSizeAndSetToView() {
        if (validator.checkUserPicSize(model.userPicFileUri)) {
            try {
                view.setImageUriToView(model.userPicFileUri);
            } catch (Exception e) {
                view.showAlertFailedToLoad();
            }
        } else {
            view.showAlertMaxSizeOfImage();
        }
    }

    @Override
    public void setUserPicToModel(Uri uri) {
        model.userPicFileUri = uri;
        model.currentPhotoPath = model.userPicFileUri.getPath();
        model.userPicFile = new File(model.currentPhotoPath);
    }

    @Override
    public void setFormatWatcher() {
        view.setPhoneMask(model.getFormatWatcher());
    }

    @Override
    public boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }



}
