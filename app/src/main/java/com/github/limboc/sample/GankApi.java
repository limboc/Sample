package com.github.limboc.sample;


import com.github.limboc.sample.data.MeizhiData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {

    @GET("/api/data/福利/{limit}/{page}")
    Observable<MeizhiData> getMeizhiData(
            @Path("limit") int limit,
            @Path("page") int page);



}
