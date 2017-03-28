package com.internship.droidz.talkin.data.web.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.internship.droidz.talkin.data.CacheSharedPreference;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

/**
 * Created by Koroqe on 21-Mar-17.
 */

public class SmackService extends Service{

    public final String TAG = "SmackService";

    public static final String NEW_MESSAGE = "newmessage";
    public static final String SEND_MESSAGE = "sendmessage";
    public static final String SEND_MESSAGE_PRIVATE = "sendmessageprivate";
    public static final String MESSAGE_ID = "message_id";
    public static final String OCCUPANT_ID = "occupant_id";
    public static final String BUNDLE_FROM_JID = "b_from";
    public static final String DIALOG_TYPE = "dialog_type";
    public static final String BUNDLE_MESSAGE_BODY = "b_body";
    public static final String BUNDLE_ROSTER = "b_body";
    public static final String BUNDLE_TO = "b_to";
    public static final String NEW_ROSTER = "newroster";

    private boolean mIsActive;
    private Thread mThread;
    private Handler mTHandler;
    private SmackConnection mConnection;
    private CacheSharedPreference mCachedSharedPreferences;
    private String mChatID;

    public SmackService(CacheSharedPreference cachedSharedPreferences) {

        this.mCachedSharedPreferences = cachedSharedPreferences;
    }

    private void initConnection(CacheSharedPreference cachedSharedPreferences) {

        Log.d("SmackConnection", "initConnection");
        if (mConnection == null) {
            Log.d("SmackConnection", "new SmackConnection");
            mConnection = new SmackConnection(cachedSharedPreferences, this, mChatID);
        }
        try {
            mConnection.establishConnection();

        } catch (IOException | SmackException | XMPPException e) {
            Log.d("SmackConnection", "error");
            e.printStackTrace();
            stopSelf();
        }
    }

    public void start() {

        if (!mIsActive) {
            mIsActive = true;
            Log.d(TAG, "start service");
            if (mThread == null || !mThread.isAlive()) {
                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        mTHandler = new Handler();
                        Log.d(TAG, "init connection");
                        initConnection(mCachedSharedPreferences);
                        Looper.loop();
                    }
                });
                mThread.start();
            }
        }
    }

    public void stop() {

        mIsActive = false;
        mTHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mConnection != null) {
                    mConnection.disconnect();
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle bundle = intent.getExtras();
        mChatID = (String) bundle.get(DIALOG_TYPE);
        start();
        return Service.START_STICKY;
        //RETURNING START_STICKY CAUSES OUR CODE TO STICK AROUND WHEN THE APP ACTIVITY HAS DIED.
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
