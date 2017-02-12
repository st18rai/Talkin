package com.internship.droidz.talkin.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Novak Alexandr on 06.02.2017.
 */

public class CacheSharedPreference {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String FILE_NAME = "CurrentUser";

    protected static final String CURRENT_USER_ID = "CURRENT_ACCOUNT_ID";
    protected static final String CURRENT_USER_TOKEN = "CURRENT_ACCOUNT_TOKEN";
    protected static final String CURRENT_USER_AVATAR = "CURRENT_ACCOUNT_AVATAR";
    protected static final String CURRENT_USER_LOGGED_IN = "CURRENT_ACCOUNT_AUTHORIZATION";
    protected static final String CURRENT_USER_PASSWORD = "CURRENT_ACCOUNT_PASSWORD";
    protected static final String CURRENT_USER_EMAIL = "CURRENT_ACCOUNT_EMAIL";
    protected static final String CURRENT_USER_KEEP_ME_SIGN_IN = "CURRENT_ACCOUNT_KEEP_ME_SIGN_IN";
    public static final String CURRENT_AVATAR = "AVATAR.jpg";

    private static CacheSharedPreference INSTANCE;

    private CacheSharedPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static CacheSharedPreference getInstance(Context context) {
        CacheSharedPreference pref = INSTANCE;
        if (pref == null) {
            synchronized (CacheSharedPreference.class) {
                pref = INSTANCE;
                if (pref == null) {
                    INSTANCE = pref = new CacheSharedPreference(context);
                }
            }
        }
        return pref;
    }

    public void putToken(String value) {
        mEditor.putString(CURRENT_USER_TOKEN, value);
        mEditor.apply();
    }

    public String getToken() {
        return mSharedPreferences.getString(CURRENT_USER_TOKEN, null);
    }

    public void putAccountAvatarBlobId(Long value) {
        mEditor.putLong(CURRENT_USER_AVATAR, value);
        mEditor.apply();
    }

    public Long getAccountAvatarBlobId() {
        long id = mSharedPreferences.getLong(CURRENT_USER_AVATAR, -1);
        if (id == -1)
            return null;
        else
            return id;
    }

    public void putIsUserAuthorized(boolean status) {
        mEditor.putBoolean(CURRENT_USER_LOGGED_IN, status);
        mEditor.apply();
    }

    public boolean isAuthorized() {
        return mSharedPreferences.getBoolean(CURRENT_USER_LOGGED_IN, false);
    }

    public void putCurrentPassword(String password) {
        mEditor.putString(CURRENT_USER_PASSWORD, password);
        mEditor.apply();
    }

    public String getCurrentPassword() {
        return mSharedPreferences.getString(CURRENT_USER_PASSWORD, null);
    }

    public void putCurrentEmail(String email) {
        mEditor.putString(CURRENT_USER_EMAIL, email);
        mEditor.apply();
    }

    public String getCurrentEmail() {
        return mSharedPreferences.getString(CURRENT_USER_EMAIL, null);
    }

    public void putCurrentFacebookId(Long id) {
        mEditor.putLong(CURRENT_USER_LOGGED_IN, id);
        mEditor.apply();
    }

    public Long getCurrentFacebookId() {
        Long id = mSharedPreferences.getLong(CURRENT_USER_LOGGED_IN, -1);
        if (id == -1)
            return null;
        else
            return id;
    }

    public void putUserId(Long id) {
        mEditor.putLong(CURRENT_USER_ID, id);
        mEditor.apply();
    }

    public Long getUserId() {
        long id = mSharedPreferences.getLong(CURRENT_USER_ID, -1);
        if (id == -1)
            return null;
        else
            return id;
    }

    public void putKeepMeLoggedIn(boolean keepMeSignIn) {
        mEditor.putBoolean(CURRENT_USER_KEEP_ME_SIGN_IN, keepMeSignIn);
        mEditor.apply();
    }

    public boolean getKeepMeSignIn() {
        return mSharedPreferences.getBoolean(CURRENT_USER_KEEP_ME_SIGN_IN, true);
    }

}
