package com.internship.droidz.talkin.data.db.model;
import com.internship.droidz.talkin.data.web.response.chat.MessageModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Novak Alexandr on 20.02.2017.
 */

public class DbMessageModel {

    Integer _id;
    Integer created_at;
    Integer updated_at;
    Integer chat_dialog_id;
    String message;
    String date_sent;
    Integer sender_id;
    Integer recipient_id;
    List<Integer> read_ids;
    List<Integer> delivered_ids;
    List<Attachment> attachments;

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

    public void setMessage(MessageModel model)
    {
        this._id=model.get_id();
        this.created_at=model.getCreated_at();
        this.updated_at=model.getUpdated_at();
        this.chat_dialog_id=model.getChat_dialog_id();
        this.message=model.getMessage();
        this.date_sent=model.getDate_sent();
        this.sender_id=model.getSender_id();
        this.recipient_id=model.getRecipient_id();
        this.read_ids=model.getRead_ids();
        this.delivered_ids=model.getDelivered_ids();
        this.attachments=convertAttachmentList(model.getAttachments());
    }


    private List<Attachment> convertAttachmentList(List<MessageModel.Attachment> list)
    {
        List<Attachment> result = new ArrayList<>();
        for(MessageModel.Attachment i:list)
        {
            if(i.getId()!=null)
                result.add(new Attachment(i.getType(),i.getId()));
            else
                result.add(new Attachment(i.getType(),i.getUrl()));
        }
        return result;
    }



    public static class Attachment
    {
       private String type;
       private Integer id;
       private String url;

        public Attachment(Attachment attachment)
        {
            this.type=attachment.getType();

            if (attachment.getId()!=null)
                this.id=attachment.getId();
            else
                this.url=attachment.getUrl();
        }

        public Attachment(String type, Integer id) {
            this.type = type;
            this.id = id;
        }

        public Attachment(String type, String url) {
            this.type = type;
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public Integer getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }
    }
}
