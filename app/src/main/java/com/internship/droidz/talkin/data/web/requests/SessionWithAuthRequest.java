package com.internship.droidz.talkin.data.web.requests;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 26.01.2017.
 */

public class SessionWithAuthRequest {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public SessionWithAuthRequest(String email, String password) {

        this.email = email;
        this.password = password;
        Log.i("debug_retrofit",this.toString());
    }


}
