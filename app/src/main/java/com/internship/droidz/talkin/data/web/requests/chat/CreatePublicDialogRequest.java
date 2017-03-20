package com.internship.droidz.talkin.data.web.requests.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public class CreatePublicDialogRequest {
    @SerializedName("type")
    @Expose
    Integer type = DialogType.PUBLIC_GROUP.getType();

    @SerializedName("photo")
    @Expose
    Integer photo_id;

    @SerializedName("name")
    @Expose
    String name;

    public CreatePublicDialogRequest(String name) {
        this.name = name;
    }

    public CreatePublicDialogRequest(String name, Integer photo_id) {
        this.name = name;
        this.photo_id = photo_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(Integer photo_id) {
        this.photo_id = photo_id;
    }
}
