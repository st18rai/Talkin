package com.internship.droidz.talkin.data.web.requests.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public class CreateGroupDialogRequest {

    @SerializedName("type")
    @Expose
    Integer type = DialogType.GROUP.getType();

    @SerializedName("occupants_ids")
    @Expose
    List<Integer> occupants_ids;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("photo")
    @Expose
    Integer photo_id;

    public CreateGroupDialogRequest(List<Integer> occupants_ids, String name) {

        this.occupants_ids = occupants_ids;
        this.name = name;
    }

    public CreateGroupDialogRequest(List<Integer> occupants_ids, String name, Integer photo_id) {
        this.occupants_ids = occupants_ids;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(Integer photo_id) {
        this.photo_id = photo_id;
    }
}
