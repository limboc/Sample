package com.github.limboc.sample.ui.item;

import android.view.ViewGroup;

import com.github.limboc.sample.ui.adapter.BaseRecyclerAdapter;


public abstract class BaseRecyclerItemFactory<ITEM extends BaseRecyclerItem>{
    protected int itemType;
    protected BaseRecyclerAdapter adapter;
    protected OnItemClickListener onItemClickListener;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public BaseRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public abstract boolean isTarget(Object itemObject);

    public abstract ITEM createBaseItem(ViewGroup parent);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
