package com.internship.droidz.talkin.web;

import com.internship.droidz.talkin.web.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public interface ApiService {
    // rest methods here

    @FormUrlEncoded
    @POST("users.json?")
    Observable<User> signUp(@Field("user[email]") String email, @Field("user[password]") String password);

}
