package com.internship.droidz.talkin.data.db.model;


import com.internship.droidz.talkin.data.web.response.user.UserSearchResponse;
import com.orm.SugarRecord;

/**
 * Created by Novak Alexandr on 28.02.2017.
 */

public class DbUserModel extends SugarRecord<DbUserModel> {

    Integer user_id;
    Integer owner_id;
    String full_name;
    String email;
    String login;
    String phone;
    String website;
    String created_at;
    String updated_at;
    String last_request_at;
    String external_user_id;
    String facebook_id;
    String twitter_id;
    String twitter_digits_id;
    String blob_id;
    String custom_data;
    String user_tags;

    public DbUserModel(Integer id, Integer owner_id, String full_name,
                       String email, String login, String phone, String website,
                       String created_at, String updated_at, String last_request_at,
                       String external_user_id, String facebook_id, String twitter_id,
                       String twitter_digits_id, String blob_id, String custom_data, String user_tags) {
        this.user_id = id;
        this.owner_id = owner_id;
        this.full_name = full_name;
        this.email = email;
        this.login = login;
        this.phone = phone;
        this.website = website;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.last_request_at = last_request_at;
        this.external_user_id = external_user_id;
        this.facebook_id = facebook_id;
        this.twitter_id = twitter_id;
        this.twitter_digits_id = twitter_digits_id;
        this.blob_id = blob_id;
        this.custom_data = custom_data;
        this.user_tags = user_tags;
    }

    public DbUserModel(UserSearchResponse.User user)
    {
        this.user_id = user.getId();
        this.owner_id = user.getOwner_id();
        this.full_name = user.getFull_name();
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.phone = user.getPhone();
        this.website = user.getWebsite();
        this.created_at =user.getCreated_at();
        this.updated_at = user.getUpdated_at();
        this.last_request_at = user.getLast_request_at();
        this.external_user_id = user.getExternal_user_id();
        this.facebook_id = user.getFacebook_id();
        this.twitter_id = user.getTwitter_id();
        this.twitter_digits_id = user.getTwitter_digits_id();
        this.blob_id = user.getBlob_id();
        this.custom_data = user.getCustom_data();
        this.user_tags = user.getUser_tags();
    }


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getLast_request_at() {
        return last_request_at;
    }

    public void setLast_request_at(String last_request_at) {
        this.last_request_at = last_request_at;
    }

    public String getExternal_user_id() {
        return external_user_id;
    }

    public void setExternal_user_id(String external_user_id) {
        this.external_user_id = external_user_id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }

    public String getTwitter_digits_id() {
        return twitter_digits_id;
    }

    public void setTwitter_digits_id(String twitter_digits_id) {
        this.twitter_digits_id = twitter_digits_id;
    }

    public String getBlob_id() {
        return blob_id;
    }

    public void setBlob_id(String blob_id) {
        this.blob_id = blob_id;
    }

    public String getCustom_data() {
        return custom_data;
    }

    public void setCustom_data(String custom_data) {
        this.custom_data = custom_data;
    }

    public String getUser_tags() {
        return user_tags;
    }

    public void setUser_tags(String user_tags) {
        this.user_tags = user_tags;
    }
}
