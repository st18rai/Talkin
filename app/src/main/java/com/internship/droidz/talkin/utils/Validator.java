package com.internship.droidz.talkin.utils;

import android.util.Log;

import java.io.File;

/**
 * Created by Koroqe on 29-Jan-17.
 */

public class Validator {

    public static float MAX_SIZE_USER_PIC = 1.0f;
    public static String PASSWORD_STRENGTH_MASK = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{0,}$";

    public static boolean checkUserPicSize(File file) {

        long size = file.length();
        Log.i("Validator", "checkUserPicUriSize: ");
        return (size / (1024 * 1024) < MAX_SIZE_USER_PIC);
    }

    public static boolean checkPasswordStrength(String password) {

        password = password.replaceFirst("[A-Z]", "")
                .replaceFirst("[a-z]", "");
        return password.matches(PASSWORD_STRENGTH_MASK);
    }

    public static boolean validateRegistrationData() {

        return true;
    }

}
