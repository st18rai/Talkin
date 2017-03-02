package com.internship.droidz.talkin.model;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.media.IMediaFile;
import com.internship.droidz.talkin.media.IMediaIntentProvider;
import com.internship.droidz.talkin.utils.Validator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

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
    private String mFacebookUserID;

    private IMediaFile media;
    private IMediaIntentProvider mediaIntentProvider;

    public RegistrationModel(IMediaIntentProvider mediaIntentProvider, IMediaFile media) {
        this.mediaIntentProvider = mediaIntentProvider;
        this.media = media;
    }

    public FormatWatcher getFormatWatcher() {

        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK);
        mFormatWatcher = new MaskFormatWatcher(MaskImpl.createTerminated(slots));
        return mFormatWatcher;
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

    public void addPicToGallery() {

        if (mUserPicFile != null) {
            App.getApp().sendBroadcast(mediaIntentProvider.getMediaScanIntent(mCurrentPhotoPath));
        } else {
            Log.i(TAG, "File doesn't exist");
        }
    }

    public Intent getCameraPictureIntent(PackageManager packageManager) {

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(packageManager) != null) {
            try {
                // mUserPicFile = createImageFile();
                mUserPicFile = media.getTemporaryFile();
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

    public File getUserPicFile() {

        return mUserPicFile;
    }

    public Uri getUserPicFileUri() {

        return mUserPicFileUri;
    }

    public void setUserPicFileUri(Uri userPicFileUri) {

        this.mUserPicFileUri = userPicFileUri;
    }


    public void setUserPicFile(File userPicFile) {

        this.mUserPicFile = userPicFile;
    }

    public String getFacebookUserID() {

        return mFacebookUserID;
    }

    public void setFacebookUserID(String mFacebookUserID) {

        this.mFacebookUserID = mFacebookUserID;
    }
}
