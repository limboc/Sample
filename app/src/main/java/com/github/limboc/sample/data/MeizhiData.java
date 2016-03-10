package com.github.limboc.sample.data;


import com.github.limboc.sample.data.bean.Meizhi;

import java.io.Serializable;
import java.util.List;


public class MeizhiData extends BaseData{
    private List<Meizhi> results;

    public List<Meizhi> getResults() {
        return results;
    }

    public void setResults(List<Meizhi> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MeizhiData{" +
                "results=" + results +
                '}';
    }
}
