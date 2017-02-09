package com.internship.droidz.talkin.utils;

import android.net.Uri;
import android.util.Log;

/**
 * Created by Koroqe on 29-Jan-17.
 */

public class Validator {

    public static float MAX_SIZE_USER_PIC = 1.0f;
    public static String PASSWORD_STRENGTH_MASK = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{0,}$";

    public boolean checkUserPicSize(Uri uri) {

        Log.i("Validator", "checkUserPicUriSize: ");
//        return size / (1024 * 1024) < MAX_SIZE_USER_PIC;
        return true;
    }

    public boolean checkPasswordStrength(String password) {
        password.replaceFirst("[A-Z]", "")
                .replaceFirst("[a-z]", "");
        return password.matches(PASSWORD_STRENGTH_MASK);
    }
}
