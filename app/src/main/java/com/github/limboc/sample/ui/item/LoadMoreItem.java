package com.github.limboc.sample.ui.item;

/**
 * Created by Administrator on 2016/11/22.
 */

import me.drakeet.multitype.Item;

/**
 * Created by Administrator on 2016/11/22.
 */

public class LoadMoreItem implements Item {

    public static final String PRELOADING = "PRELOADING", LOADING = "LOADING", ERROR = "ERROR", END = "END";
    private String status;


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public LoadMoreItem(String status) {
        this.status = status;
    }
}
