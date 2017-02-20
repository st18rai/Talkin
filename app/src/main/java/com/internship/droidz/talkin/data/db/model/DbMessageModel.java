package com.internship.droidz.talkin.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.droidz.talkin.data.db.RealmInteger;
import com.internship.droidz.talkin.data.web.response.chat.MessageModel;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Novak Alexandr on 20.02.2017.
 */

public class DbMessageModel extends RealmObject {

    Integer _id;
    Integer created_at;
    Integer updated_at;
    Integer chat_dialog_id;
    String message;
    String date_sent;
    Integer sender_id;
    Integer recipient_id;
    RealmList<RealmInteger> read_ids;
    RealmList<RealmInteger> delivered_ids;
    RealmList<Attachment> attachments;

    public static class Attachment extends RealmObject
    {
        String type;
        Integer id;
        String url;

        public Attachment(String type, Integer id) {
            this.type = type;
            this.id = id;
        }

        public Attachment(String type, String url) {
            this.type = type;
            this.url = url;
        }
    }
}
