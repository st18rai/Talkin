package com.internship.droidz.talkin.data.web;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.internship.droidz.talkin.data.web.service.ContentService;
import com.internship.droidz.talkin.data.web.service.UserService;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


/**
 * Created by Novak Alexandr on 19.01.2017.
 */

public class ApiRetrofit {

    private static final String SCHEMA = "http://";
    private static final String HOST = "api.quickblox.com/";
    public static final String APP_ID = "52558";
    public static final String APP_AUTH_KEY= "UwScdfTjL7Tbhu5";
    public static final String APP_SECRET= "zbhG3xMwDPrWyzf";
    private static final String SRV = SCHEMA + HOST;

    public static String TOKEN_HEADER="QB-Token";

    HttpLoggingInterceptor logging;

    private static ApiRetrofit INSTANCE;



    public UserService getUserService() {
        return userService;
    }

    public ContentService getContentService() {
        return contentService;
    }

    private UserService userService;
    private ContentService contentService;


    private ApiRetrofit() {
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = createClient();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SRV)
                .client(okHttpClient)
                .addConverterFactory(new QualifiedTypeConverterFactory(
                        GsonConverterFactory.create(gson),
                        SimpleXmlConverterFactory.create()
                ))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        userService=retrofit.create(UserService.class);
        contentService=retrofit.create(ContentService.class);
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(logging);
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


