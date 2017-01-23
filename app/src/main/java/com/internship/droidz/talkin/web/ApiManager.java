package com.internship.droidz.talkin.web;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public class ApiManager {

    private static final String SCHEMA = "https://";
    private static final String HOST = "api.quickblox.com/";
    public static final String APP_ID = "52558";
    public static final String APP_AUTH_KEY= "UwScdfTjL7Tbhu5";
    public static final String APP_SECRET= "zbhG3xMwDPrWyzf";
    private static final String SRV=SCHEMA+HOST;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static ApiService service;

    private  static GsonConverterFactory createGsonConverter() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        return GsonConverterFactory.create(builder.create());
    }

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(SRV)
                    .addConverterFactory(createGsonConverter())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()));
                    //.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static ApiService getService()
    {
        if(service==null){
            return createService(ApiService.class);
        }
        else {
            return service;
        }


    }

}
