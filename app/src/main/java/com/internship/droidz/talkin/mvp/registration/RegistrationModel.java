package com.internship.droidz.talkin.mvp.registration;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

/**
 * Created by st18r on 20.01.2017.
 */

public class RegistrationModel implements RegistrationContract.RegistrationModel {

    private final String TAG = "RegistrationModel";
    private static final String PHONE_MASK = "+38 (0__) ___-__-__";

    public String currentPhotoPath;
    public Uri userPicFileUri;
    public File userPicFile;
    private FormatWatcher mFormatWatcher;
    private CallbackManager mCallbackManager;

    private String mFacebookUserID;


    @Override
    public File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        userPicFile = new File(storageDir + "/" + imageFileName + ".jpg");
        currentPhotoPath = userPicFile.getAbsolutePath();
        return userPicFile;
    }

    public FormatWatcher getFormatWatcher() {

        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK);
        mFormatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        return mFormatWatcher;
    }

    @Override
    public void linkFacebook(LoginButton linkFacebookButtonReg, RegistrationModelListener listener) {

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

    @Override
    public Intent getMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File userPicFile = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(userPicFile);
        mediaScanIntent.setData(contentUri);
        return mediaScanIntent;
    }

    @Override
    public void grantAllPermissionsToUserPicFile(Intent intent) {

        List<ResolveInfo> resInfoList = App.getApp().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            App.getApp().grantUriPermission(packageName, userPicFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    @Override
    public void setUserPic(Uri uri) {

        userPicFileUri = uri;
        userPicFile = new File(uri.getPath());
        currentPhotoPath = userPicFile.getAbsolutePath();
    }

    @Override
    public Intent getCameraPictureIntent(PackageManager packageManager) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(packageManager) != null) {
            try {
                userPicFile = createImageFile();
            } catch (IOException e) {
                Log.i(TAG, "Can't create file!", e);
            }
            if (userPicFile != null) {
                userPicFileUri = FileProvider.getUriForFile(App.getApp(),
                        "com.internship.droidz.talkin.fileprovider",
                        userPicFile);
                grantAllPermissionsToUserPicFile(pictureIntent);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, userPicFileUri);
            }
        }
        return pictureIntent;
    }
}
