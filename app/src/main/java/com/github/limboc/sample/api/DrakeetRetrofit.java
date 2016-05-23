package com.github.limboc.sample.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class DrakeetRetrofit {

    final GankApi gankService;
    final long TIME_OUT = 10;
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    DrakeetRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                //.addNetworkInterceptor(mTokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gankService = retrofit.create(GankApi.class);
    }

    /*Interceptor mTokenInterceptor = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
                return chain.proceed(originalRequest);
            }
            Request authorised = originalRequest.newBuilder()
                    .header("Authorization", Your.sToken)
                    .build();
            return chain.proceed(authorised);
        }
    };*/


    public GankApi getGankService() {
        return gankService;
    }


}
