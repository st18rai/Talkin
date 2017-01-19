package com.internship.droidz.talkin.web;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public class ApiManager {

    private static final String SCHEMA = "https://";
    private static final String HOST = "api.quickblox.com/";
    private static final String APP_ID = "52558";
    private static final String APP_AUTH_KEY= "UwScdfTjL7Tbhu5";
    private static final String APP_SECRET= "zbhG3xMwDPrWyzf";
    private static final String SRV=SCHEMA+HOST;


    private Retrofit retrofit;
    private ApiService apiService;

    public void init()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(SRV)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(createGsonConverter())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private GsonConverterFactory createGsonConverter() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        return GsonConverterFactory.create(builder.create());
    }
}
