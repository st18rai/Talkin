package com.internship.droidz.talkin.data.web.response.file;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Novak Alexandr on 30.01.2017.
 */

@Root(name = "PostResponse")
public class UploadFileResponse {
    @Element(name = "Location")
    private String location;

    @Element(name = "Bucket")
    private String bucket;

    @Element(name = "Key")
    private String key;

    @Element(name = "ETag")
    private String eTag;

    public UploadFileResponse(){}

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}