package com.internship.droidz.talkin.utils;

import android.net.Uri;

import java.io.File;

/**
 * Created by Koroqe on 29-Jan-17.
 */

public class Validator {

    float MAX_SIZE_USER_PIC = 1.0f;

    public boolean checkUserPicUriSize(Uri uri) {
        File f = new File(uri.getPath());
        long size = f.length();
        return size / (1024 * 1024) < MAX_SIZE_USER_PIC;
    }
}
