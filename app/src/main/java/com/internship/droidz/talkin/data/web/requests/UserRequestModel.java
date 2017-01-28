package com.internship.droidz.talkin.data.web.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 26.01.2017.
 */

public class UserRequestModel {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;



    public UserRequestModel(String email, String password) {

        this.email = email;
        this.password = password;
    }
}
