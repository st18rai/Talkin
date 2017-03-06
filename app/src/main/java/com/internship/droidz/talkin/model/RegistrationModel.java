package com.internship.droidz.talkin.model;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.media.IMediaFile;
import com.internship.droidz.talkin.media.PhotoFile;
import com.internship.droidz.talkin.utils.Converter;
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

    private FormatWatcher mFormatWatcher;
    private String mFacebookUserID;
    private IMediaFile userPic;

    public FormatWatcher getFormatWatcher() {

        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK);
        mFormatWatcher = new MaskFormatWatcher(MaskImpl.createTerminated(slots));
        return mFormatWatcher;
    }

    public Intent getMediaScanIntent() {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(userPic.getUri());
        return mediaScanIntent;
    }

    public void grantAllPermissionsToUserPicFile(Intent intent) {

        List<ResolveInfo> resInfoList = App.getApp().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            App.getApp().grantUriPermission(packageName, userPic.getUri(), Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    public boolean setUserPic(Uri uri) {

        userPic = new PhotoFile();
        userPic.setFile(new File(Converter.getRealPathFromURI(uri)));

        if (userPic.getFile() != null) {
            if (Validator.checkUserPicSize(userPic.getFile())) {
                return true;
            } else {
                userPic = null;
                return false;
            }
        } else {
            Log.i(TAG, "setUserPic: File doesn't exist");
            return false;
        }
    }

    public boolean addPicToGallery() {

        if (userPic.getFile() != null) {
            if (Validator.checkUserPicSize(userPic.getFile())) {
                App.getApp().sendBroadcast(getMediaScanIntent());
                return true;
            }
            userPic = null;
            return false;
        } else {
            Log.i(TAG, "addPicToGallery: File doesn't exist");
            userPic = null;
            return false;
        }
    }

    public Intent getCameraPictureIntent(IMediaFile mediaFile, PackageManager packageManager) {

        userPic = mediaFile;
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(packageManager) != null) {
            try {
                userPic.setFile(mediaFile.createFile());

            } catch (IOException e) {
                Log.i(TAG, "Can't create file!", e);
            }
            if (userPic.getFile() != null) {
                userPic.getFile();
                grantAllPermissionsToUserPicFile(pictureIntent);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, userPic.getUri());
                Log.i(TAG, "getCameraPictureIntent: intent with extra");
            } else {
                Log.i(TAG, "File doesn't exist");
            }
        }
        return pictureIntent;
    }

    public IMediaFile getUserPic() {

        return userPic;
    }

    public String getFacebookUserID() {

        return mFacebookUserID;
    }

    public void setFacebookUserID(String mFacebookUserID) {

        this.mFacebookUserID = mFacebookUserID;
    }
}
