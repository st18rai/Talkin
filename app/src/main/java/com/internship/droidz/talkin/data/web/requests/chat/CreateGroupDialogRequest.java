package com.internship.droidz.talkin.data.web.requests.chat;

import android.util.Log;

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
    String occupants_ids;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("photo")
    @Expose
    Integer photo_id;

    public CreateGroupDialogRequest(List<Integer> occupants_ids, String name) {

        this.occupants_ids =convertList( occupants_ids);
        this.name = name;
    }

    public CreateGroupDialogRequest(List<Integer> occupants_ids, String name, Integer photo_id) {
        this.occupants_ids = convertList(occupants_ids);
        this.name = name;
        this.photo_id = photo_id;
    }

    private String convertList(List<Integer> list)
    {
        String result="";
        for (Integer i:list)
        {
            result=result+i.toString()+",";
        }
        result=result.substring(0,result.length()-1);
        Log.i("debug_occupants",result);
        return result;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOccupants_ids() {
        return occupants_ids;
    }

    public void setOccupants_ids(List<Integer> occupants_ids) {
        this.occupants_ids = convertList(occupants_ids);
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
