package com.internship.droidz.talkin.data.web.service;

import com.internship.droidz.talkin.data.model.SessionModel;
import com.internship.droidz.talkin.data.web.requests.SessionRequest;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Novak Alexandr on 26.01.2017.
 */

public interface SessionService {

    @Headers({"Content-Type: application/json"})
    @POST("/session.json")
    Observable<SessionModel> getSession(@Body SessionRequest body);


}
