package com.github.limboc.sample.ui.adapter;

import android.support.annotation.NonNull;
import com.github.limboc.refresh.OnLoadMoreListener;
import com.github.limboc.sample.ui.item.LoadMoreItem;
import com.github.limboc.sample.ui.provider.LoadMoreProvider;
import java.util.List;
import me.drakeet.multitype.Item;
import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.TypePool;

/**
 * Created by Administrator on 2016/11/22.
 */

public class BaseAdapter extends MultiTypeAdapter {
    public BaseAdapter(@NonNull List<? extends Item> items, OnLoadMoreListener onLoadMoreListener) {
        super(items);
        register(LoadMoreItem.class, new LoadMoreProvider(onLoadMoreListener));
    }


    public BaseAdapter(@NonNull List<? extends Item> items, TypePool pool, OnLoadMoreListener onLoadMoreListener) {
        super(items, pool);
        register(LoadMoreItem.class, new LoadMoreProvider(onLoadMoreListener));
    }



}
