package com.github.limboc.sample;


import com.github.limboc.sample.data.SimpleResult;
import com.github.limboc.sample.data.bean.Meizhi;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankApi {

    @GET("/api/data/福利/{limit}/{page}")
    Observable<SimpleResult<List<Meizhi>>> getMeizhiData(
            @Path("limit") int limit,
            @Path("page") int page);



}
