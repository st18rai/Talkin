package com.internship.droidz.talkin.web.model;

/**
 * Created by Novak Alexandr on 23.01.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("session")
    @Expose
    private Session_ session;

    public Session_ getSession() {
        return session;
    }

    public void setSession(Session_ user) {
        this.session = user;
    }
}
