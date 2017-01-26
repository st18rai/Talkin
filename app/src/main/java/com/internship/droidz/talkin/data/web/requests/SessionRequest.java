package com.internship.droidz.talkin.data.web.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;

/**
 * Created by Novak Alexandr on 26.01.2017.
 */
public class SessionRequest {
    @SerializedName("application_id")
    @Expose
    protected String applicationId;

    @SerializedName("auth_key")
    @Expose
    protected String authKey;


    @SerializedName("nonce")
    @Expose
    protected String nonce;

    @SerializedName("timestamp")
    @Expose
    protected String timestamp;

    @SerializedName("signature")
    @Expose
    protected String signature;

    public SessionRequest(String applicationId, String authKey, String nonce, String timestamp, String signature) {
        this.applicationId = applicationId;
        this.authKey = authKey;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.signature = signature;
    }

    public SessionRequest() {
        Long timestamp = System.currentTimeMillis()/1000l;
        int nonce = WebUtils.getNonce();
        this.applicationId=ApiRetrofit.APP_ID;
        this.authKey=ApiRetrofit.APP_AUTH_KEY;
        this.nonce=String.valueOf(nonce);
        this.timestamp=String.valueOf(timestamp);
        this.signature=WebUtils.calcSignature(nonce,timestamp);
    }

    public String getSignature() {
        return signature;
    }

    public String getAuthKey() {
        return authKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public String getApplicationId() {
        return applicationId;
    }

    @Override public String toString() {
        return "SessionRequest{" +
                "applicationId='" + applicationId + '\'' +
                ", authKey='" + authKey + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}