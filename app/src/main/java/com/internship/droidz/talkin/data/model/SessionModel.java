package com.internship.droidz.talkin.data.model;

/**
 * Created by Novak Alexandr on 23.01.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionModel {

    @SerializedName("session")
    @Expose
    private SessionObject session;

    public SessionObject getSession() {
        return session;
    }

    public void setSession(SessionObject session) {
        this.session = session;
    }

    public class SessionObject {

        @SerializedName("application_id")
        @Expose
        private String application_id;

        @SerializedName("created_at")
        @Expose
        private String created_at;

        @SerializedName("device_id")
        @Expose
        private Integer device_id;

        @SerializedName("nonce")
        @Expose
        private Integer nonce;

        @SerializedName("token")
        @Expose
        private String token;

        @SerializedName("ts")
        @Expose
        private Integer ts;

        @SerializedName("updated_at")
        @Expose
        private String updated_at;

        @SerializedName("user_id")
        @Expose
        private Integer user_id;

        @SerializedName("id")
        @Expose
        private Integer id;

        public String getApplication_id() {
            return application_id;
        }

        public void setApplication_id(String application_id) {
            this.application_id = application_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public Integer getDevice_id() {
            return device_id;
        }

        public void setDevice_id(Integer device_id) {
            this.device_id = device_id;
        }

        public Integer getNonce() {
            return nonce;
        }

        public void setNonce(Integer nonce) {
            this.nonce = nonce;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getTs() {
            return ts;
        }

        public void setTs(Integer ts) {
            this.ts = ts;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
