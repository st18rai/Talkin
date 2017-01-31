package com.internship.droidz.talkin.data.web.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 30.01.2017.
 */

public class RegistrationRequest {

    @SerializedName("user")
    @Expose
    private UserSignUpRequest user;

    public UserSignUpRequest getUser() {
        return user;
    }

    public void setUser(UserSignUpRequest user) {
        this.user = user;
    }

    public RegistrationRequest(UserSignUpRequest user) {
        this.user = user;
    }
}
