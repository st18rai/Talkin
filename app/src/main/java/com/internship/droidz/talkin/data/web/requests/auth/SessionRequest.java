package com.internship.droidz.talkin.data.web.requests.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.signature = signature;
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