package com.github.limboc.sample.ui.item;

import android.view.ViewGroup;

import com.github.limboc.sample.ui.adapter.AssemblyRecyclerAdapter;


public abstract class AssemblyRecyclerItemFactory<ITEM extends AssemblyRecyclerItem>{
    protected int itemType;
    protected AssemblyRecyclerAdapter adapter;
    protected OnItemClickListener onItemClickListener;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public AssemblyRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(AssemblyRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public abstract boolean isTarget(Object itemObject);

    public abstract ITEM createAssemblyItem(ViewGroup parent);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
