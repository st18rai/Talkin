package com.internship.droidz.talkin.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.internship.droidz.talkin.App;

/**
 * Created by Koroqe on 06-Mar-17.
 */

public class Converter {

    public static String getRealPathFromURI(Uri contentUri) {

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = App.getApp().getContentResolver().query(contentUri,  proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
}
