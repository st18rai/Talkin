package com.internship.droidz.talkin.data.web.response.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public class MessageModel {
    @SerializedName("_id")
    @Expose
    Integer _id;

    @SerializedName("created_at")
    @Expose
    Integer created_at;

    @SerializedName("updated_at")
    @Expose
    Integer updated_at;

    @SerializedName("chat_dialog_id")
    @Expose
    Integer chat_dialog_id;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("date_sent")
    @Expose
    String date_sent;

    @SerializedName("sender_id")
    @Expose
    Integer sender_id;

    @SerializedName("recipient_id")
    @Expose
    Integer recipient_id;

    @SerializedName("read_ids")
    @Expose
    List<Integer> read_ids;

    @SerializedName("delivered_ids")
    @Expose
    List<Integer> delivered_ids;

    @SerializedName("attachments")
    @Expose
    List<Attachment> attachments;

    public static class Attachment
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

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Integer created_at) {
        this.created_at = created_at;
    }

    public Integer getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Integer updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getChat_dialog_id() {
        return chat_dialog_id;
    }

    public void setChat_dialog_id(Integer chat_dialog_id) {
        this.chat_dialog_id = chat_dialog_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public Integer getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(Integer recipient_id) {
        this.recipient_id = recipient_id;
    }

    public List<Integer> getRead_ids() {
        return read_ids;
    }

    public void setRead_ids(List<Integer> read_ids) {
        this.read_ids = read_ids;
    }

    public List<Integer> getDelivered_ids() {
        return delivered_ids;
    }

    public void setDelivered_ids(List<Integer> delivered_ids) {
        this.delivered_ids = delivered_ids;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
