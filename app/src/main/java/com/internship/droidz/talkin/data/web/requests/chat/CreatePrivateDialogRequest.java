package com.internship.droidz.talkin.data.web.requests.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public class CreatePrivateDialogRequest {

    @SerializedName("type")
    @Expose
    Integer type = DialogType.PRIVATE.getType();

    @SerializedName("occupants_ids")
    @Expose
    List<Integer> occupants_ids;

    @SerializedName("photo")
    @Expose
    Integer photo_id;

    public CreatePrivateDialogRequest(List<Integer> occupants_ids) {
        this.occupants_ids = occupants_ids;
    }

    public CreatePrivateDialogRequest(List<Integer> occupants_ids, Integer photo_id) {
        this.occupants_ids = occupants_ids;
        this.photo_id = photo_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getOccupants_ids() {
        return occupants_ids;
    }

    public void setOccupants_ids(List<Integer> occupants_ids) {
        this.occupants_ids = occupants_ids;
    }

    public Integer getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(Integer photo_id) {
        this.photo_id = photo_id;
    }
}
