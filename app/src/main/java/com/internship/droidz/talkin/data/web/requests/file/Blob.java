package com.internship.droidz.talkin.data.web.requests.file;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 30.01.2017.
 */

public class Blob {

    @SerializedName("content_type")
    @Expose
    private String contentType;

    @SerializedName("name")
    @Expose
    private String name;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
