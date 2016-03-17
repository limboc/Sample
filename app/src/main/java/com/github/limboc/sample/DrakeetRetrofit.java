package com.github.limboc.sample;

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

    // @formatter:off
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    // @formatter:on


    DrakeetRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                //.addNetworkInterceptor(mTokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.gank.io/")
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
