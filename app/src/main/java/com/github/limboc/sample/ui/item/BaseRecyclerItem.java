package com.github.limboc.sample.ui.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRecyclerItem<BEAN, ITEM_FACTORY extends BaseRecyclerItemFactory> extends RecyclerView.ViewHolder{
    protected ITEM_FACTORY itemFactory;
    protected BEAN data;

    protected BaseRecyclerItem(View convertView, ITEM_FACTORY itemFactory) {
        super(convertView);
        if(itemFactory == null){
            throw new IllegalArgumentException("param itemFactory is null");
        }
        this.itemFactory = itemFactory;
        onFindViews(convertView);
        onConfigViews(convertView.getContext());
        if(null != itemFactory.onItemClickListener){
            convertView.setOnClickListener((listener)-> itemFactory.onItemClickListener.onClick(getPosition()));
        }

    }

    public void setData(int position, BEAN bean){
        this.data = bean;
        onSetData(position, bean);
    }

    protected abstract void onFindViews(View convertView);

    protected abstract void onConfigViews(Context context);

    protected abstract void onSetData(int position, BEAN bean);

    public final View getConvertView(){
        return itemView;
    }

    public ITEM_FACTORY getItemFactory() {
        return itemFactory;
    }

    public BEAN getData() {
        return data;
    }


}