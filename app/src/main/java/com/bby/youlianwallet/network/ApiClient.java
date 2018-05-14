package com.bby.youlianwallet.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 刘涛 on 2017/8/7.
 */

public class ApiClient {

    private static Retrofit imAdapter;
    public final static String API_URL="http://mobile.betaofwallet.com/";
//    public final static String API_URL="http://192.168.0.105:8080/";

    private static OkHttpClient client;
    static HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();

    public static ApiService getApiAdapter(){
        if (imAdapter == null) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.addInterceptor(loggingInterceptor);
            builder.build();
            client = builder.build();
            imAdapter = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        ApiService apiService = imAdapter.create(ApiService.class);
        return apiService;
    }

}
