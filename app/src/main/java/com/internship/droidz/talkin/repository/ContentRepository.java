package com.internship.droidz.talkin.repository;

import android.net.UrlQuerySanitizer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.internship.droidz.talkin.App;
import com.internship.droidz.talkin.data.CacheSharedPreference;
import com.internship.droidz.talkin.data.web.AmazonConstants;
import com.internship.droidz.talkin.data.web.ApiRetrofit;
import com.internship.droidz.talkin.data.web.requests.user.UpdateUserRequest;
import com.internship.droidz.talkin.data.web.requests.file.CreateFileRequest;
import com.internship.droidz.talkin.data.web.requests.file.FileConfirmUploadRequest;
import com.internship.droidz.talkin.data.web.response.file.CreateFileResponse;
import com.internship.droidz.talkin.data.web.response.file.GetFileResponse;
import com.internship.droidz.talkin.data.web.response.file.UploadFileResponse;
import com.internship.droidz.talkin.data.web.service.ContentService;
import com.internship.droidz.talkin.data.web.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Novak Alexandr on 06.02.2017.
 */

public class ContentRepository {

    private String name;
    private CacheSharedPreference cache;
    private volatile String blobId = "";
    private ContentService contentService;
    private UserService userService;

    public ContentRepository(ApiRetrofit retrofitApi) {

        this.contentService =retrofitApi.getContentService();
        this.userService=retrofitApi.getUserService();
        cache = CacheSharedPreference.getInstance(App.getApp().getApplicationContext());
    }

    public Observable<Response<Void>> uploadAvatar(String contentType, File file, String name) {

        CreateFileRequest.Blob blob = new CreateFileRequest.Blob(contentType, file.getName());
        CreateFileRequest fileCreateRequest = new CreateFileRequest(blob);
        Log.i("rx","inside create file");

        return contentService.createFile( fileCreateRequest,cache.getToken())
                .flatMap(new Func1<CreateFileResponse, Observable<UploadFileResponse>>() {
                    @Override
                    public Observable<UploadFileResponse> call(CreateFileResponse createFileResponse) {
                        blobId = createFileResponse.getBlob().getId();
                        String params = createFileResponse.getBlob().getBlobObjectAccess().getParams();
                        params = params.replaceAll("&amp;", "&");
                        Map<String, RequestBody> paramsMap = composeFormParamsMap(params);
                        MultipartBody.Part filePart = prepareFilePart(file, contentType, name);
                        return contentService.uploadFile(AmazonConstants.AMAZON_END_POINT, paramsMap, filePart);
                    }
                })
                .flatMap(new Func1<UploadFileResponse, Observable<Response<Void>>>() {
                    @Override
                    public Observable<Response<Void>> call(UploadFileResponse uploadFileResponse) {
                        String size = String.valueOf(file.getTotalSpace());
                        FileConfirmUploadRequest.Blob confirmBlob = new FileConfirmUploadRequest.Blob(size);
                        FileConfirmUploadRequest confirmRequest = new FileConfirmUploadRequest(confirmBlob);

                        if (name.equals(cache.CURRENT_AVATAR))
                            cache.putAccountAvatarBlobId(Long.parseLong(blobId));

                        return contentService.fileConfirmUpload(blobId,cache.getToken(), confirmRequest)
                                .flatMap(new Func1<Response<Void>, Observable<Response<Void>>>() {
                                    @Override
                                    public Observable<Response<Void>> call(Response<Void> response) {
                                        Log.i("rx","inside update user");
                                        return userService.updateUser(cache.getUserId().toString(),
                                                new UpdateUserRequest(new UpdateUserRequest.User(blobId)),
                                                cache.getToken());
                                    }
                                });
                    }
                });
    }

    public void downloadFile(String id, String token,File dstFile)
    {
        contentService.downloadFile(id,token)
                .flatMap(new Func1<Response<ResponseBody>, Observable<File>>() {
                    @Override
                    public Observable<File> call(Response<ResponseBody> responseBodyResponse) {
                        return saveFile(responseBodyResponse,dstFile);
                    }
                });
    }

    public Observable<File> saveFile(Response<ResponseBody> response,File dstFile) {
        return Observable.create((Observable.OnSubscribe<File>) subscriber -> {
            try {
                BufferedSink sink = Okio.buffer(Okio.sink(dstFile));
                sink.writeAll(response.body().source());
                sink.close();
                subscriber.onNext(dstFile);
                subscriber.onCompleted();
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

    public Observable<GetFileResponse> uploadFile(String contentType, File file, String name)
    {
        CreateFileRequest.Blob blob = new CreateFileRequest.Blob(contentType, file.getName());
        CreateFileRequest fileCreateRequest = new CreateFileRequest(blob);
        return contentService.createFile( fileCreateRequest,cache.getToken())
                .flatMap(new Func1<CreateFileResponse, Observable<UploadFileResponse>>() {
                    @Override
                    public Observable<UploadFileResponse> call(CreateFileResponse createFileResponse) {
                        blobId = createFileResponse.getBlob().getId();
                        String params = createFileResponse.getBlob().getBlobObjectAccess().getParams();
                        params = params.replaceAll("&amp;", "&");
                        Map<String, RequestBody> paramsMap = composeFormParamsMap(params);
                        MultipartBody.Part filePart = prepareFilePart(file, contentType, name);
                        return contentService.uploadFile(AmazonConstants.AMAZON_END_POINT, paramsMap, filePart);
                    }
                })
                .flatMap(new Func1<UploadFileResponse, Observable<Response<Void>>>() {
                    @Override
                    public Observable<Response<Void>> call(UploadFileResponse uploadFileResponse) {
                        String size = String.valueOf(file.getTotalSpace());
                        FileConfirmUploadRequest.Blob confirmBlob = new FileConfirmUploadRequest.Blob(size);
                        FileConfirmUploadRequest confirmRequest = new FileConfirmUploadRequest(confirmBlob);
                        return contentService.fileConfirmUpload(blobId,cache.getToken(), confirmRequest);
                    }
                })
                .flatMap(new Func1<Response<Void>, Observable<GetFileResponse>>() {
                    @Override
                    public Observable<GetFileResponse> call(Response<Void> voidResponse) {
                        return contentService.getFile(blobId,cache.getToken());
                    }
                });
    }

    private RequestBody createPartFromString(String source) {

        return RequestBody.create(MediaType.parse("text/plain"), source);
    }

    private Map<String, RequestBody> composeFormParamsMap(String source) {

        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.registerParameter(AmazonConstants.AMAZON_EXPIRES, UrlQuerySanitizer.getSpaceLegal());
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseUrl(source);
        Map<String, RequestBody> result = new HashMap<>();

        result.put(AmazonConstants.AMAZON_CONTENT_TYPE,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_CONTENT_TYPE)));
        result.put(AmazonConstants.AMAZON_EXPIRES,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_EXPIRES)));
        result.put(AmazonConstants.AMAZON_ACL,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_ACL)));
        result.put(AmazonConstants.AMAZON_KEY,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_KEY)));
        result.put(AmazonConstants.AMAZON_POLICY,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_POLICY)));
        result.put(AmazonConstants.AMAZON_ACTION_STATUS,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_ACTION_STATUS)));
        result.put(AmazonConstants.AMAZON_ALGORITHM,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_ALGORITHM)));
        result.put(AmazonConstants.AMAZON_CREDENTIAL,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_CREDENTIAL)));
        result.put(AmazonConstants.AMAZON_DATE,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_DATE)));
        result.put(AmazonConstants.AMAZON_SIGNATURE,
                createPartFromString(sanitizer.getValue(AmazonConstants.AMAZON_SIGNATURE)));

        return result;
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(File file, String contentType, String name) {

        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);
        if (name == null)
            this.name = file.getName();
        else
            this.name = name;
        return MultipartBody.Part.createFormData(
                AmazonConstants.AMAZON_FILE,
                this.name,
                requestFile);
    }
}