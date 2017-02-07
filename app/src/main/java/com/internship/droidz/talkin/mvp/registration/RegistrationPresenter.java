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

import java.io.File;
import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
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
                                        signIn(email,password);
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
    public void uploadPhoto(Uri photoUri, String email, String password) {

    }

    /*@Override
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
                                Log.i("retrofit err_sess,",((HttpException) e).response().errorBody().string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else
                        {
                            Log.i("error session","msg: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(SessionModel sessionModel) {
                        int nonce= WebUtils.getNonce();
                        long timestamp = System.currentTimeMillis()/1000l;
                        SessionWithAuthRequest request=  new SessionWithAuthRequest(
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
                                        Log.i("photo","authorized");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (e instanceof HttpException) {
                                            try
                                            {
                                                Log.i("retrofit err_auth,",((HttpException) e).response().errorBody().string());
                                            } catch (IOException e1) {
                                                //e1.printStackTrace();
                                            }
                                        }
                                        else
                                        {
                                            Log.i("error_auth","error "+e.getMessage());
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onNext(SessionModel sessionModel) {
                                        String token=sessionModel.getSession().getToken();
                                        Blob blob = new Blob("image/jpeg","avatar");
                                        CreateFileRequest createFileRequest = new CreateFileRequest(blob);
                                        System.out.println();
                                        ApiRetrofit.getRetrofitApi().getContentService().createFile(createFileRequest,token)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(createFileResponse -> {
                                                    Map<String,RequestBody> map= new HashMap<>();
                                                    service.getContentService().uploadFile(createFileResponse.getBlob().getBlobObjectAccess().getParams(),
                                                            map, prepareFilePart("partName", model.userPicFile))
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Subscriber<UploadFileResponse>() {
                                                        @Override
                                                        public void onCompleted() {
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            if (e instanceof HttpException) {
                                                                try
                                                                {
                                                                    Log.i("retrofit upl_file,",((HttpException) e).response().errorBody().string());
                                                                  //  Toast.makeText((LoginScreen)view,"Wrong login or password",Toast.LENGTH_LONG).show();
                                                                } catch (IOException e1) {
                                                                    //e1.printStackTrace();
                                                                }
                                                            }
                                                            else
                                                            {
                                                                Log.i("error_upl_file","error: "+e.getMessage());
                                                                //e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onNext(UploadFileResponse uploadFileResponse) {
                                                        service.getContentService().fileConfirmUpload(createFileResponse.getBlob().getId(),
                                                                token,
                                                                new FileConfirmUploadRequest(new FileConfirmUploadRequest.Blob(createFileResponse.getBlob().getSize())))
                                                                .subscribeOn(Schedulers.io())
                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                .subscribe(new Subscriber<Response<Void>>() {
                                                                    @Override
                                                                    public void onCompleted() {

                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        if (e instanceof HttpException) {
                                                                            try
                                                                            {
                                                                                Log.i("retrofit confirm_file,",((HttpException) e).response().errorBody().string());
                                                    //                            Toast.makeText((LoginScreen)view,"Wrong login or password",Toast.LENGTH_LONG).show();
                                                                            } catch (IOException e1) {
                                                  //                              e1.printStackTrace();
                                                                            }
                                                                        }
                                                                        else
                                                                        {
                                                                            Log.i("error_confirm_file","error: "+e.getMessage());
                                                                            e.printStackTrace();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onNext(Response<Void> voidResponse) {
                                                                        service.getUserService().updateUser(sessionModel.getSession().getUser_id().toString(),
                                                                                new UpdateUserRequest(new UpdateUserRequest.User(createFileResponse.getBlob().getId())),
                                                                                token)
                                                                                .subscribeOn(Schedulers.io())
                                                                                .observeOn(AndroidSchedulers.mainThread())
                                                                                .subscribe(new Subscriber<UserModel>() {
                                                                                    @Override
                                                                                    public void onCompleted() {

                                                                                    }

                                                                                    @Override
                                                                                    public void onError(Throwable e) {
                                                                                        if (e instanceof HttpException) {
                                                                                            try
                                                                                            {
                                                                                                Log.i("retrofit update_user,",((HttpException) e).response().errorBody().string());
                                                                                                Toast.makeText((LoginScreen)view,"Wrong login or password",Toast.LENGTH_LONG).show();
                                                                                            } catch (IOException e1) {
                                                                                                e1.printStackTrace();
                                                                                            }
                                                                                        }
                                                                                        else
                                                                                        {
                                                                                            Log.i("error_update_user","error: "+e.getMessage());
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void onNext(UserModel userModel) {
                                                                                        Log.i("user","user id: "+userModel.getUser().getId());
                                                                                        Log.i("user","user blob: "+userModel.getUser().getBlobId());

                                                                                    }
                                                                                });

                                                                    }
                                                                }

                                                               );
                                                        }
                                                    });
                                                });
                                    }
                                });
                    }
                });
    }
*/

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


    public void signIn(String email, String password) {

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
                                        if (model.userPicFile != null) {
                                            uploadUserPic();
                                        }
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
                                        else {
                                            Log.i("error","some error");
                                        }
                                    }

                                    @Override
                                    public void onNext(SessionModel sessionModel) {
                                        Log.i("user",String.valueOf(sessionModel.getSession().getUser_id()));
                                        cache.putToken(String.valueOf(sessionModel.getSession().getToken()));
                                    }
                                });
                    }
                });
    }

    @Override
    public void uploadUserPic() {

        ContentRepository repo = new ContentRepository(ApiRetrofit.getRetrofitApi());
        Log.i("USERPICFILECANREAD", "" + model.userPicFile.canRead());
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
}
