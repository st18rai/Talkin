package com.internship.droidz.talkin.data.web.response.file;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 20.02.2017.
 */

public class GetFileResponse {

    @SerializedName("blob")
    @Expose
    private Blob blob;

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }



    public static class Blob
    {
        @SerializedName("blob_status")
        @Expose
        String blob_status;

        @SerializedName("content_type")
        @Expose
        String content_type;

        @SerializedName("created_at")
        @Expose
        String created_at;

        @SerializedName("id")
        @Expose
        Integer id;

        @SerializedName("last_read_access_ts")
        @Expose
        String last_read_access_ts;

        @SerializedName("lifetime")
        @Expose
        String lifetime;

        @SerializedName("name")
        @Expose
        String  name;

        @SerializedName("public")
        @Expose
        String ispublic;

        @SerializedName("ref_count")
        @Expose
        Integer ref_count;

        @SerializedName("set_completed_at")
        @Expose
        String set_completed_at;

        @SerializedName("size")
        @Expose
        Integer size;

        @SerializedName("uid")
        @Expose
        String  uid;

        @SerializedName("updated_at")
        @Expose
        String updated_at;

        public String getBlob_status() {
            return blob_status;
        }

        public void setBlob_status(String blob_status) {
            this.blob_status = blob_status;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLast_read_access_ts() {
            return last_read_access_ts;
        }

        public void setLast_read_access_ts(String last_read_access_ts) {
            this.last_read_access_ts = last_read_access_ts;
        }

        public String getLifetime() {
            return lifetime;
        }

        public void setLifetime(String lifetime) {
            this.lifetime = lifetime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIspublic() {
            return ispublic;
        }

        public void setIspublic(String ispublic) {
            this.ispublic = ispublic;
        }

        public Integer getRef_count() {
            return ref_count;
        }

        public void setRef_count(Integer ref_count) {
            this.ref_count = ref_count;
        }

        public String getSet_completed_at() {
            return set_completed_at;
        }

        public void setSet_completed_at(String set_completed_at) {
            this.set_completed_at = set_completed_at;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}