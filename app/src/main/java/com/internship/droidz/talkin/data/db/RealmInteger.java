package com.internship.droidz.talkin.data.db;

import io.realm.RealmObject;

/**
 * Created by Novak Alexandr on 20.02.2017.
 */

public class RealmInteger extends RealmObject {

    Integer value;

    public RealmInteger(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
