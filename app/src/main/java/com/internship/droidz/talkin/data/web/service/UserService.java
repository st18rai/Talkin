package com.internship.droidz.talkin.data.web.service;

import com.internship.droidz.talkin.data.model.SessionModel;

import com.internship.droidz.talkin.data.model.UserModel;
import com.internship.droidz.talkin.data.web.requests.auth.RegistrationRequest;
import com.internship.droidz.talkin.data.web.requests.auth.SessionRequest;
import com.internship.droidz.talkin.data.web.requests.auth.SessionWithAuthRequest;
import com.internship.droidz.talkin.data.web.requests.user.ResetPasswordRequest;
import com.internship.droidz.talkin.data.web.requests.user.UpdateUserRequest;
import com.internship.droidz.talkin.data.web.requests.user.UserSearchRequest;
import com.internship.droidz.talkin.data.web.response.user.UserSearchResponse;


import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Novak Alexandr on 26.01.2017.
 */

public interface UserService {

    @Headers({"Content-Type: application/json"})
    @POST("/session.json")
    Observable<SessionModel> getSession(@Body SessionRequest body);

    @Headers({"Content-Type: application/json"})
    @POST("/session.json")
    Observable<SessionModel> getSessionWithAuth(@Body SessionWithAuthRequest body, @Header("QB-Token") String token);

    @Headers({"Content-Type: application/json"})
    @POST("/login.json")
    Observable<SessionModel> requestLogin(@Body SessionWithAuthRequest body, @Header("QB-Token") String token);

    @Headers({"Content-Type: application/json"})
    @POST("/users.json")
    Observable<UserModel> requestSignUp(@Body RegistrationRequest body, @Header("QB-Token") String token);

    @Headers({"Content-Type: application/json"})
    @PUT("/users/{user_id}.json")
    Observable<Response<Void>> updateUser(@Path(value = "user_id") String userId,
                                          @Body UpdateUserRequest body,
                                          @Header("QB-Token") String token);

    @Headers({"Content-Type: application/json"})
    @GET("/users/password/reset.json")
    Observable<Response<Void>> resetPassword(@Body ResetPasswordRequest body, @Header("QB-Token") String token);


    @Headers({"Content-Type: application/json"})
    @GET("/users/by_full_name.json")
    Observable<UserSearchResponse> searchUser(@Query("full_name") String full_name, @Header("QB-Token") String token);
}
