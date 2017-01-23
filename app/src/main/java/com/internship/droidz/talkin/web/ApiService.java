package com.internship.droidz.talkin.web;

import com.internship.droidz.talkin.web.model.Session;
import com.internship.droidz.talkin.web.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("users.json?")
    Observable<User> signUp(@Field("user[email]") String email, @Field("user[password]") String password,@Field("token") String token);

    @FormUrlEncoded
    @POST("session.json?")
    Observable<Session> createSession(@Field("application_id") String app_id, @Field("auth_key") String auth_key,
      @Field("nonce") Integer nonce, @Field("timestamp") Long timestamp,  @Field("signature") String signature);

}
