package com.internship.droidz.talkin.media;

import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.internship.droidz.talkin.App;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by serg on 3/2/17.
 */

public class PhotoFile implements IMediaFile {

    private String mCurrentPhotoPath;
    private Uri mUri;
    private File mFile;

    @Override
    public String getFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return storageDir + File.separator + imageFileName + ".jpg";
    }

    @Override
    public File createFile() {
        setFile(new File(getFileName()));
        return mFile;
    }

    @Override
    public File getFile() {
        return mFile;
    }

    @Override
    public Uri getUri() {
        return mUri;
    }

    @Override
    public void setFile(File file) {
        mFile = file;
        mCurrentPhotoPath = mFile.getPath();
        mUri = FileProvider.getUriForFile(App.getApp(),
                "com.internship.droidz.talkin.fileprovider",
                mFile);
    }

}
