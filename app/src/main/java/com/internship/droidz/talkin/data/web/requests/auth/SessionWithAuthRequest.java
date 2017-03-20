package com.internship.droidz.talkin.data.web.requests.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.WebUtils;
import com.internship.droidz.talkin.data.web.requests.user.UserRequestModel;

/**
 * Created by Novak Alexandr on 26.01.2017.
 */

public class SessionWithAuthRequest {

    @SerializedName("user")
    @Expose
    private UserRequestModel user;


    @SerializedName("user[email]")
    @Expose
    private String email;

    @SerializedName("user[password]")
    @Expose
    private String password;

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


    public SessionWithAuthRequest(UserRequestModel user, String applicationId, String authKey, String nonce, String timestamp, String signature) {
        this.user = user;
        this.applicationId = applicationId;
        this.authKey = authKey;
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.signature = signature;
    }

    public SessionWithAuthRequest(String email, String password) {
        Long timestamp = System.currentTimeMillis()/1000l;
        int nonce = WebUtils.getNonce();
        this.timestamp=String.valueOf(timestamp);
        this.nonce=String.valueOf(nonce);
        this.applicationId= ApiRetrofit.APP_ID;
        this.authKey=ApiRetrofit.APP_AUTH_KEY;
        this.nonce=String.valueOf(nonce);
        this.timestamp=String.valueOf(timestamp);
        this.signature=WebUtils.calcSignature(nonce,timestamp,email,password);
        this.user=new UserRequestModel(email,password);




    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRequestModel getUser() {
        return user;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getAuthKey() {
        return authKey;
    }

    public String getNonce() {
        return nonce;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSignature() {
        return signature;
    }
}
