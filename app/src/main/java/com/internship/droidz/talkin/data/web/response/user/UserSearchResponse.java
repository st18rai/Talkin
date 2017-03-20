package com.internship.droidz.talkin.data.web.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Novak Alexandr on 28.02.2017.
 */

public class UserSearchResponse {

    @SerializedName("current_page")
    @Expose
    Integer current_page;

    @SerializedName("per_page")
    @Expose
    Integer per_page;

    @SerializedName("total_entries")
    @Expose
    Integer total_entries;

    @SerializedName("items")
    @Expose
    List<Item> item;

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getTotal_entries() {
        return total_entries;
    }

    public void setTotal_entries(Integer total_entries) {
        this.total_entries = total_entries;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public static class Item
    {
        @SerializedName("user")
        @Expose
        User user;


        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class User {

        @SerializedName("id")
        @Expose
        Integer id;

        @SerializedName("owner_id")
        @Expose
        Integer owner_id;

        @SerializedName("full_name")
        @Expose
        String full_name;

        @SerializedName("email")
        @Expose
        String email;

        @SerializedName("login")
        @Expose
        String login;

        @SerializedName("phone")
        @Expose
        String phone;

        @SerializedName("website")
        @Expose
        String website;

        @SerializedName("created_at")
        @Expose
        String created_at;

        @SerializedName("updated_at")
        @Expose
        String updated_at;

        @SerializedName("last_request_at")
        @Expose
        String last_request_at;

        @SerializedName("external_user_id")
        @Expose
        String external_user_id;

        @SerializedName("facebook_id")
        @Expose
        String facebook_id;

        @SerializedName("twitter_id")
        @Expose
        String twitter_id;

        @SerializedName("twitter_digits_id")
        @Expose
        String twitter_digits_id;

        @SerializedName("blob_id")
        @Expose
        String blob_id;

        @SerializedName("custom_data")
        @Expose
        String custom_data;

        @SerializedName("user_tags")
        @Expose
        String user_tags;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public Integer getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(Integer owner_id) {
            this.owner_id = owner_id;
        }
    }

}
