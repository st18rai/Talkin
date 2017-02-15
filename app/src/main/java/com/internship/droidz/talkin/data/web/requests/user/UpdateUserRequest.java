package com.internship.droidz.talkin.data.web.requests.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 01.02.2017.
 */

public class UpdateUserRequest {

    @SerializedName("user")
    @Expose
    private User user;

    public UpdateUserRequest(User user) {
        this.user = user;
    }

    public static class User {

        @SerializedName("blob_id")
        @Expose
        String blob_id;

        public User(String blob_id) {
            this.blob_id = blob_id;
        }

        public String getBlob_id() {
            return blob_id;
        }

        public void setBlob_id(String blob_id) {
            this.blob_id = blob_id;
        }
    }
}
