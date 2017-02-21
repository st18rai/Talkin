package com.internship.droidz.talkin.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by Koroqe on 29-Jan-17.
 */

public class Validator {

    private static float MAX_SIZE_USER_PIC = 1.0f;
    private static String PASSWORD_STRENGTH_MASK = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{0,}$";

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

    public static boolean validateRegistrationData(String email, String password, String fullName, String phone, String website) {

        if (isValidEmail(email) && isValidPasswordLength(password) && checkPasswordStrength(password)) {
            return  true;
        } else return false;
    }


    public static boolean isValidEmail(CharSequence target) {

        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isValidPasswordLength(String password) {

        if (password != null && password.length() > 6) {
            return true;
        }
        return false;
    }


}
