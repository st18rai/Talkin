package com.internship.droidz.talkin.data.web.requests.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public class ResetPasswordRequest {

    @SerializedName("email")
    @Expose
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ResetPasswordRequest(String email) {
        this.email = email;
    }
}
