package com.internship.droidz.talkin.data.web.service;

import com.internship.droidz.talkin.data.CacheSharedPreference;

import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * Created by Koroqe on 21-Mar-17.
 */

public class SmackService {

    public final String service = "chat.quickblox.com";
    private CacheSharedPreference mCachedSharedPreferences;

    public SmackService(CacheSharedPreference mCachedSharedPreferences) {

        this.mCachedSharedPreferences = mCachedSharedPreferences;
    }


    public void establishConnection() {

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(String.valueOf(mCachedSharedPreferences.getUserId()),
                        mCachedSharedPreferences.getCurrentPassword())
                .setServiceName(service)
                .setPort(8222)
                .build();
    }
}
