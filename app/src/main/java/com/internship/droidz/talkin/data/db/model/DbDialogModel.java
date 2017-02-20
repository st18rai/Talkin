package com.internship.droidz.talkin.data.db.model;

import com.internship.droidz.talkin.data.db.RealmInteger;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Novak Alexandr on 20.02.2017.
 */

public class DbDialogModel extends RealmObject {

    Integer _id;

    Integer user_id;

    Integer created_at;

    Integer updated_at;

    Integer type;

    String name;

    String photo;

    String xmpp_room_jid;

    RealmList<RealmInteger> occupants_ids;

    String last_message;

    String last_message_date_sent;

    Integer last_message_user_id;

    Integer unread_messages_count;
}
