package com.internship.droidz.talkin.data.web.requests.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 28.02.2017.
 */

public class UserSearchRequest {

    @SerializedName("full_name")
    @Expose
    String full_name;

    @SerializedName("page")
    @Expose
    Integer page= 3;
    @SerializedName("per_page")
    @Expose
    Integer perPage=15;

    public UserSearchRequest(String full_name) {
        this.full_name = full_name;
    }

    public UserSearchRequest(String full_name, Integer page, Integer perPage) {
        this.full_name = full_name;
        this.page = page;
        this.perPage = perPage;
    }
}
