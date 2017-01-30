package com.internship.droidz.talkin.data.web.service;

import com.internship.droidz.talkin.data.web.requests.file.CreateFileRequest;
import com.internship.droidz.talkin.data.web.response.file.CreateFileResponse;
import com.internship.droidz.talkin.data.web.response.file.UploadFileResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Novak Alexandr on 30.01.2017.
 */

public interface ContentService {

    @Headers({"Content-Type: application/json"})
    @POST("/blobs.json")
    Observable<CreateFileResponse> createFile(@Body CreateFileRequest request, @Header("QB-Token") String token);


    @Multipart
    @POST
    Observable<UploadFileResponse> uploadFile(@Url String url, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);
}
