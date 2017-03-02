package com.internship.droidz.talkin.media;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by serg on 3/2/17.
 */

public class PhotoFile implements IMediaFile {

    @Override
    public String getFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return storageDir + File.separator + imageFileName + ".jpg";
    }

    @Override
    public File getTemporaryFile() {
        return new File(getFileName());
    }
}
