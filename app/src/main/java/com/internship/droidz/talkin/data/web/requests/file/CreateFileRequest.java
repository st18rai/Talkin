package com.internship.droidz.talkin.data.web.requests.file;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 30.01.2017.
 */

public class CreateFileRequest {
    @SerializedName("blob")
    @Expose
    private Blob blob;

    public CreateFileRequest(Blob blob) {
        this.blob = blob;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }
}
