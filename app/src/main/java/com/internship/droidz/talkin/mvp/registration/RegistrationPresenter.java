package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.model.UserModel;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.RegistrationRequest;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.UpdateUserRequest;
import com.internship.droidz.talkin.data.web.requests.UserRequestModel;
import com.internship.droidz.talkin.data.web.requests.UserSignUpRequest;
import com.internship.droidz.talkin.data.web.requests.file.Blob;
import com.internship.droidz.talkin.data.web.requests.file.CreateFileRequest;
import com.internship.droidz.talkin.data.web.requests.file.FileConfirmUploadRequest;
import com.internship.droidz.talkin.data.web.response.file.UploadFileResponse;
import com.internship.droidz.talkin.mvp.login.LoginScreen;
import com.internship.droidz.talkin.utils.Validator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by st18r on 20.01.2017.
 */


public class RegistrationPresenter implements RegistrationContract.RegistrationPresenter {

    private String TAG = "RegistrationPresenter";

    Context context;
    RegistrationModel model;
    RegistrationContract.RegistrationView view;


    private Validator validator = new Validator();

    public RegistrationPresenter(RegistrationModel model, RegistrationContract.RegistrationView view, Context context) {
        this.model = model;
        this.view = view;
        this.context = context;
    }

    @Override
    public void signUp(String email, String password, String fullName, String phone, String website) {
        int nonce= WebUtils.getNonce();
        long timestamp = System.currentTimeMillis()/1000l;
        ApiRetrofit.getRetrofitApi().getUserService()
                .getSession(new SessionRequest(ApiRetrofit.APP_ID, ApiRetrofit.APP_AUTH_KEY,
                        String.valueOf(nonce), String.valueOf(timestamp), WebUtils.calcSignature(nonce, timestamp)))
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
                                password, fullName, phone, website);
                        RegistrationRequest request = new RegistrationRequest(requestReg);
                        ApiRetrofit.getRetrofitApi().getUserService().requestSignUp(request,sessionModel.getSession().getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<UserModel>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.i("registration", "registered");
                                        view.navigateToMainScreen();
                                        uploadPhoto(model.userPicFileUri, email, password);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.i("registration","failed :( " + e.getMessage());
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
    public void uploadPhoto(Uri photoUri, String email,String password) {
        MultipartBody.Part body = prepareFilePart("userPhoto", photoUri);
        HashMap<String, RequestBody> map = new HashMap<>();
        ApiRetrofit service=ApiRetrofit.getRetrofitApi();
        //login
        int nonce= WebUtils.getNonce();
        long timestamp = System.currentTimeMillis()/1000l;
        ApiRetrofit.getRetrofitApi().getUserService()
                .getSession(new SessionRequest(ApiRetrofit.APP_ID, ApiRetrofit.APP_AUTH_KEY,
                        String.valueOf(nonce), String.valueOf(timestamp), WebUtils.calcSignature(nonce,timestamp)))

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //  .subscribe(sessionModel -> {})
                .subscribe(new Subscriber<SessionModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("error", "message: " + e.getMessage());
                        if (e instanceof HttpException) {
                            try
                            {
                                Log.i("retrofit error,", ((HttpException) e).response().errorBody().string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {
                        int nonce = WebUtils.getNonce();
                        long timestamp = System.currentTimeMillis()/1000l;
                        SessionWithAuthRequest request = new SessionWithAuthRequest(
                                new UserRequestModel(email, password),
                                ApiRetrofit.APP_ID,
                                ApiRetrofit.APP_AUTH_KEY,
                                String.valueOf(nonce),
                                String.valueOf(timestamp),
                                WebUtils.calcSignature(nonce, timestamp,
                                        email,
                                        password));

                        ApiRetrofit.getRetrofitApi().getUserService()
                                .getSessionWithAuth(request, sessionModel.getSession().getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<SessionModel>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (e instanceof HttpException) {
                                            try
                                            {
                                                Log.i("retrofit error,",((HttpException) e).response().errorBody().string());
                                                Toast.makeText((LoginScreen)view,"Wrong login or password",Toast.LENGTH_LONG).show();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        else
                                        {
                                            Log.i("error","some error");
                                        }

                                    }

                                    @Override
                                    public void onNext(SessionModel sessionModel) {
                                        String token=sessionModel.getSession().getToken();
                                        Blob blob = new Blob("image/*","avatar");
                                        CreateFileRequest createFileRequest = new CreateFileRequest(blob);

                                        service.getContentService().createFile(createFileRequest,token)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(createFileResponse -> {
                                                    Map<String,RequestBody> map= new HashMap<>();
                                                    service.getContentService().uploadFile(createFileResponse.getBlob().getBlobObjectAccess().getParams(),
                                                            map, prepareFilePart("partName", photoUri))
                                                    .subscribe(new Subscriber<UploadFileResponse>() {
                                                        @Override
                                                        public void onCompleted() {
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                        }

                                                        @Override
                                                        public void onNext(UploadFileResponse uploadFileResponse) {
                                                        service.getContentService().fileConfirmUpload(createFileResponse.getBlob().getId(),
                                                                token,
                                                                new FileConfirmUploadRequest(new FileConfirmUploadRequest.Blob(createFileResponse.getBlob().getSize())))
                                                                .subscribe(voidResponse -> {
                                                                    service.getUserService().updateUser(sessionModel.getSession().getUser_id().toString(),
                                                                            new UpdateUserRequest(new UpdateUserRequest.User(createFileResponse.getBlob().getId())),
                                                                            token);
                                                                     });
                                                        }
                                                    });
                                                });

                                    }
                                });
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
        setUserPicUri(intent.getData());
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
    public void setUserPicUri(Uri uri) {
        model.userPicFileUri = uri;
    }

    @Override
    public void setFormatWatcher() {
        view.setPhoneMask(model.getFormatWatcher());
    }

    @Override
    public boolean shouldAskPermission(){

        return(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri.getPath());

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"), model.userPicFile);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }



}
