package com.internship.droidz.talkin.data.db.model;

import com.internship.droidz.talkin.data.web.response.chat.DialogModel;
import com.orm.SugarRecord;

import java.util.List;


/**
 * Created by Novak Alexandr on 20.02.2017.
 */

public class DbDialogModel extends SugarRecord<DbDialogModel> {


    public void setDialog(DialogModel model)
    {
        this._id=model.get_id();
        this.user_id=model.getUser_id();
        this.created_at=model.getCreated_at();
        this.updated_at=model.getUpdated_at();
        this.type=model.getType();
        this.name=model.getName();
        this.photo=model.getPhoto();
        this.xmpp_room_jid=model.getXmpp_room_jid();
        this.occupants_ids=model.getOccupants_ids();
        this.last_message=model.getLast_message();
        this.last_message_date_sent=model.getLast_message_date_sent();
        this.last_message_user_id=model.getLast_message_user_id();
        this.unread_messages_count=model.getUnread_messages_count();
    }

    public DbDialogModel() {}

    public DbDialogModel(String _id, Integer user_id, String created_at,
                         String updated_at, Integer type, String name,
                         String photo, String xmpp_room_jid, List<Integer> occupants_ids,
                         String last_message, String last_message_date_sent,
                         Integer last_message_user_id, Integer unread_messages_count) {
        this._id = _id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.type = type;
        this.name = name;
        this.photo = photo;
        this.xmpp_room_jid = xmpp_room_jid;
        this.occupants_ids = occupants_ids;
        this.last_message = last_message;
        this.last_message_date_sent = last_message_date_sent;
        this.last_message_user_id = last_message_user_id;
        this.unread_messages_count = unread_messages_count;
    }

    String _id;

    Integer user_id;

    String created_at;

    String updated_at;

    Integer type;

    String name;

    String photo;

    String xmpp_room_jid;

    List<Integer> occupants_ids;

    String last_message;

    String last_message_date_sent;

    Integer last_message_user_id;

    Integer unread_messages_count;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getXmpp_room_jid() {
        return xmpp_room_jid;
    }

    public void setXmpp_room_jid(String xmpp_room_jid) {
        this.xmpp_room_jid = xmpp_room_jid;
    }

    public List<Integer> getOccupants_ids() {
        return occupants_ids;
    }

    public void setOccupants_ids(List<Integer> occupants_ids) {
        this.occupants_ids = occupants_ids;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_message_date_sent() {
        return last_message_date_sent;
    }

    public void setLast_message_date_sent(String last_message_date_sent) {
        this.last_message_date_sent = last_message_date_sent;
    }

    public Integer getLast_message_user_id() {
        return last_message_user_id;
    }

    public void setLast_message_user_id(Integer last_message_user_id) {
        this.last_message_user_id = last_message_user_id;
    }

    public Integer getUnread_messages_count() {
        return unread_messages_count;
    }

    public void setUnread_messages_count(Integer unread_messages_count) {
        this.unread_messages_count = unread_messages_count;
    }
}
