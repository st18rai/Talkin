package com.internship.droidz.talkin.mvp.registration;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

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
    FormatWatcher formatWatcher;

    @Override
    public void getAllPermissionsToUserPicFile(Context context, Intent intent) {

        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, userPicFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    @Override
    public void addPicToGallery(Context context) {

        if (currentPhotoPath != null) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            userPicFile = new File(currentPhotoPath);
            Uri contentUri = Uri.fromFile(userPicFile);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } else {
            Log.i(TAG, "File don't exist");
        }
    }

    @Override
    public File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        userPicFile = new File(storageDir + imageFileName + ".jpg");
        currentPhotoPath = userPicFile.getAbsolutePath();
        return userPicFile;
    }

    @Override
    public FormatWatcher getFormatWatcher() {

        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK);
        formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        return formatWatcher;
    }

}
