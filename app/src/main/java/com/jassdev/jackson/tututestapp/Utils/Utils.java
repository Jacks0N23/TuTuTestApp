package com.jassdev.jackson.tututestapp.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Helper Class for some staff
 */

public class Utils {

    public static <T> T createRxService(final Class<T> rxService, boolean log) {
        if (log) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.connectTimeout(15, TimeUnit.SECONDS);
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            httpClient.retryOnConnectionFailure(true);
            Retrofit rxRetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl("https://github.com/")
                    .build();
            return rxRetrofit.create(rxService);
        } else {
            return new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://github.com/")
                    .build()
                    .create(rxService);
        }
    }
}
