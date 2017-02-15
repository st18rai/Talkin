package com.internship.droidz.talkin.data.web.service;

import com.internship.droidz.talkin.data.web.requests.chat.CreateGroupDialogRequest;
import com.internship.droidz.talkin.data.web.requests.chat.CreatePrivateDialogRequest;
import com.internship.droidz.talkin.data.web.requests.chat.CreatePublicDialogRequest;
import com.internship.droidz.talkin.data.web.requests.file.CreateFileRequest;
import com.internship.droidz.talkin.data.web.response.chat.DialogModel;
import com.internship.droidz.talkin.data.web.response.chat.GetDialogResponse;
import com.internship.droidz.talkin.data.web.response.file.CreateFileResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Novak Alexandr on 15.02.2017.
 */

public interface ChatService {

    @Headers("Content-Type: application/json")
    @POST("/chat/Dialog.json")
    Observable<DialogModel> createDialog(@Body CreateGroupDialogRequest request, @Header("QB-Token") String token);

    @Headers("Content-Type: application/json")
    @POST("/chat/Dialog.json")
    Observable<DialogModel> createDialog(@Body CreatePrivateDialogRequest request, @Header("QB-Token") String token);

    @Headers("Content-Type: application/json")
    @POST("/chat/Dialog.json")
    Observable<DialogModel> createDialog(@Body CreatePublicDialogRequest request, @Header("QB-Token") String token);

    @Headers("Content-Type: application/json")
    @GET("/chat/Dialog.json")
    Observable<GetDialogResponse> getAllDialogs(@Header("QB-Token") String token);

}
