package com.internship.droidz.talkin.data.web.requests.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 30.01.2017.
 */

public class UserSignUpRequest {


    @SerializedName("password")
    @Expose
    private String password = null;
    @SerializedName("email")
    @Expose
    private String email = null;
    @SerializedName("full_name")
    @Expose
    private String fullName = null;
    @SerializedName("phone")
    @Expose
    private String phone = null;
    @SerializedName("website")
    @Expose
    private String website = null;

    @SerializedName("facebook_id")
    @Expose
    private String facebook_id = null;


    public UserSignUpRequest(String email, String password, String fullName, String phone, String website) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.website = website;
    }

    public UserSignUpRequest(String email, String password, String fullName, String phone, String website, String facebook_id) {
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.website = website;
        this.facebook_id = facebook_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
