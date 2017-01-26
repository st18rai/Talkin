package com.internship.droidz.talkin.data.web;


import com.internship.droidz.talkin.data.web.service.SessionService;
import com.internship.droidz.talkin.data.web.service.UserService;



import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public class ApiRetrofit {

    private static final String SCHEMA = "https://";
    private static final String HOST = "api.quickblox.com/";
    public static final String APP_ID = "52558";
    public static final String APP_AUTH_KEY= "UwScdfTjL7Tbhu5";
    public static final String APP_SECRET= "zbhG3xMwDPrWyzf";
    private static final String SRV=SCHEMA+HOST;

    public static String TOKEN_HEADER="QB-Token";

    private static ApiRetrofit INSTANCE;

    private SessionService sessionService;

    public UserService getUserService() {
        return userService;
    }

    private UserService userService;


    public SessionService getSessionService() {
        return sessionService;
    }

    private ApiRetrofit() {

        OkHttpClient okHttpClient = createClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SRV)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        sessionService = retrofit.create(SessionService.class);
        userService=retrofit.create(UserService.class);
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }


 public static ApiRetrofit getRetrofitApi() {
        if (INSTANCE == null) {
            synchronized (ApiRetrofit.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiRetrofit();
                }
            }
        }
        return INSTANCE;
    }


    }


