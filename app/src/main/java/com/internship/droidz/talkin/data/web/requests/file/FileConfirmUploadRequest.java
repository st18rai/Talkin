package com.internship.droidz.talkin.data.web.requests.file;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 31.01.2017.
 */

public class FileConfirmUploadRequest {
    @SerializedName("blob")
    @Expose
    private Blob blob;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public FileConfirmUploadRequest(Blob blob) {
        this.blob = blob;
    }

    public static class Blob{

        public Blob(String size) {
            this.size = size;
        }

        @SerializedName("size")
        @Expose
        private String size;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
