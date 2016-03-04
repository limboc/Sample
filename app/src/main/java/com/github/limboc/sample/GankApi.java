package com.github.limboc.sample;


import com.github.limboc.sample.data.MeizhiData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {

    @GET("/api/data/福利/" + DrakeetFactory.meizhiSize + "/{page}")
    Observable<MeizhiData> getMeizhiData(
            @Path("page") int page);



}
