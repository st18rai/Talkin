package com.internship.droidz.talkin.model;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.utils.Validator;

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

    public void addPicToGallery() {

        if (mUserPicFile != null) {
            App.getApp().sendBroadcast(getMediaScanIntent());;
        } else {
            Log.i(TAG, "File doesn't exist");
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
