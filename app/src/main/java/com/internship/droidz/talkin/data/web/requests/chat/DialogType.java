package com.internship.droidz.talkin.data.web.requests.chat;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public enum DialogType {

    PUBLIC_GROUP(1),
    GROUP(2),
    PRIVATE(3);

    private int type;

    DialogType(int type)
    {
        this.type=type;
    }

    public int getType()
    {
        return  type;
    }
}
