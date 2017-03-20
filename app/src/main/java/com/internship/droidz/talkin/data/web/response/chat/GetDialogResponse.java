package com.internship.droidz.talkin.data.web.response.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public class GetDialogResponse {

    @SerializedName("limit")
    @Expose
    Integer limit;

    @SerializedName("skip")
    @Expose
    Integer skip;

    @SerializedName("items")
    @Expose
    List<DialogModel> items;

    public GetDialogResponse(Integer limit, Integer skip, List<DialogModel> items) {
        this.limit = limit;
        this.skip = skip;
        this.items = items;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public List<DialogModel> getItems() {
        return items;
    }

    public void setItems(List<DialogModel> items) {
        this.items = items;
    }
}
