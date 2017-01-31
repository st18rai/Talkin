package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.model.UserModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.RegistrationRequest;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.UserSignUpRequest;
import com.internship.droidz.talkin.utils.Validator;

import java.io.IOException;

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

    private Validator validator = new Validator();

    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view, Context context) {
        this.model = model;
        this.view = view;
        this.context = context;
    }

    @Override
    public void showDialogChooseSource() {
        CharSequence sourcesOfImage[] = new CharSequence[] {"Device Camera", "Photo Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose source:")
                .setNegativeButton("BACK", (DialogInterface dialog, int which) -> {})
                .setItems(sourcesOfImage, (DialogInterface dialog, int which) -> {
                    if (which == 0) {
                        view.startCameraForCapture();
                    }
                    if (which == 1) {
                        view.startGalleryForCapture();
                    }
                })
                .show();
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
                    public void onCompleted() {

                    }

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
                                        view.navigatetoMainScreen();

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
        setUserPicUri(intent.getData());
        checkImageSizeAndSetToView();
    }

    @Override
    public void checkImageSizeAndSetToView() {
        if (validator.checkUserPicUriSize(model.userPicFileUri)) {
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
    public void setUserPicUri(Uri uri) {
        model.userPicFileUri = uri;
    }

    @Override
    public void setFormatWatcher() {
        view.setPhoneMask(model.getFormatWatcher());
    }

}
