package com.internship.droidz.talkin.model;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

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
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.presentation.presenter.registration.RegistrationListener;
import com.internship.droidz.talkin.repository.ContentRepository;
import com.internship.droidz.talkin.repository.SessionRepository;
import com.internship.droidz.talkin.utils.Validator;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by st18r on 10.02.2017.
 */

public class RegistrationModel {

    private final String TAG = "RegistrationModel";
    private static final String PHONE_MASK = "+38 (0__) ___-__-__";

    private String mCurrentPhotoPath;
    private Uri mUserPicFileUri;
    private File mUserPicFile;
    private FormatWatcher mFormatWatcher;
    private CallbackManager mCallbackManager;
    CacheSharedPreference cache = CacheSharedPreference.getInstance(App.getApp().getApplicationContext());

    private String mFacebookUserID="";

    public File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mUserPicFile = new File(storageDir + File.separator + imageFileName + ".jpg");
        mCurrentPhotoPath = mUserPicFile.getAbsolutePath();
        return mUserPicFile;
    }

    public FormatWatcher getFormatWatcher() {

        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK);
        mFormatWatcher = new MaskFormatWatcher(MaskImpl.createTerminated(slots));
        return mFormatWatcher;
    }

    private void signUpWithPhoto(RegistrationListener listener, String email, String password, String fullName, String phone, String website)
    {
        SessionRepository sessionRepository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        ContentRepository contentRepository  = new ContentRepository(ApiRetrofit.getRetrofitApi());

        sessionRepository.signUp(email,password,fullName,phone,website,mFacebookUserID)
                .flatMap(new Func1<SessionModel, Observable<Response<Void>>>() {
                    @Override
                    public Observable<Response<Void>> call(SessionModel sessionModel) {
                        cache.putToken(sessionModel.getSession().getToken());
                        cache.putUserId(Long.valueOf(sessionModel.getSession().getUser_id()));
                        return contentRepository.uploadFile(AmazonConstants.CONTENT_TYPE_JPEG,
                                mUserPicFile, cache.CURRENT_AVATAR);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Void>>() {

                    @Override
                    public void onCompleted() {

                        Log.i("victory","user created, ava uploaded and updated");
                        listener.onRegistrationCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit registration,",((HttpException) e).response().errorBody().string());
                                listener.onRegistrationError();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            Log.i("error_reg_user","error: "+e.getMessage());
                            listener.onNetworkError();
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onNext(Response<Void> voidResponse) {

                    }
                });
    }

    private void signUpWithoutPhoto(RegistrationListener listener, String email, String password, String fullName, String phone, String website)
    {
        SessionRepository sessionRepository = new SessionRepository(ApiRetrofit.getRetrofitApi());
        sessionRepository.signUp(email,password,fullName,phone,website,mFacebookUserID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SessionModel>() {

                    @Override
                    public void onCompleted() {

                        Log.i("victory","user created");
                        listener.onRegistrationCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            try {
                                Log.i("retrofit registration,",((HttpException) e).response().errorBody().string());
                                listener.onRegistrationError();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            Log.i("error_reg_user","error: "+e.getMessage());
                            listener.onNetworkError();
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


    public void signUp(RegistrationListener listener, String email, String password, String fullName, String phone, String website) {
        if(mUserPicFile==null)
        {
            signUpWithoutPhoto(listener, email, password, fullName, phone, website);
        }
        else
        {
           signUpWithPhoto(listener, email, password, fullName, phone, website);
        }
    }

    public void linkFacebook(LoginButton linkFacebookButtonReg, RegistrationListener listener) {

        mCallbackManager = CallbackManager.Factory.create();
        linkFacebookButtonReg.performClick();
        linkFacebookButtonReg.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i(TAG, "onSuccess: " + accessToken);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            response.toString();
                            try {
                                mFacebookUserID = object.getString("id");
                                Log.i(TAG, "Facebook linked: " + mFacebookUserID);
                                listener.onFacebookLogin();
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

    public Intent getMediaScanIntent() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File userPicFile = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(userPicFile);
        mediaScanIntent.setData(contentUri);
        return mediaScanIntent;
    }

    public void grantAllPermissionsToUserPicFile(Intent intent) {

        List<ResolveInfo> resInfoList = App.getApp().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            App.getApp().grantUriPermission(packageName, mUserPicFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    public void setUserPic(Uri uri) {

        String path = getRealPathFromURI(uri);
        File file = new File(path);

        if (Validator.checkUserPicSize(file)) {
            mUserPicFileUri = uri;
            mCurrentPhotoPath = path;
            mUserPicFile = file;
        } else {
            mUserPicFile = null;
            mUserPicFileUri = null;
        }
    }

    public Intent getCameraPictureIntent(PackageManager packageManager) {

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(packageManager) != null) {
            try {
                mUserPicFile = createImageFile();
                mCurrentPhotoPath = mUserPicFile.getPath();
            } catch (IOException e) {
                Log.i(TAG, "Can't create file!", e);
            }
            if (mUserPicFile != null) {
                mUserPicFileUri = FileProvider.getUriForFile(App.getApp(),
                        "com.internship.droidz.talkin.fileprovider",
                        mUserPicFile);
                grantAllPermissionsToUserPicFile(pictureIntent);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUserPicFileUri);
                Log.i(TAG, "getCameraPictureIntent: intent with extra");
            }
        }
        return pictureIntent;
    }

    private String getRealPathFromURI(Uri contentUri) {

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = App.getApp().getContentResolver().query(contentUri,  proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    public File getmUserPicFile() {

        return mUserPicFile;
    }

    public Uri getUserPicFileUri() {

        return mUserPicFileUri;
    }

    public void setUserPicFileUri(Uri userPicFileUri) {

        this.mUserPicFileUri = userPicFileUri;
    }


    public void setmUserPicFile(File mUserPicFile) {

        this.mUserPicFile = mUserPicFile;
    }
}
