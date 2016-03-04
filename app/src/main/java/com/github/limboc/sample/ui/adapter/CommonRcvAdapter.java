package com.github.limboc.sample.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.limboc.sample.ui.item.AdapterItem;

import java.util.ArrayList;
import java.util.List;



public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter implements IAdapter<T> {

    private List<T> mDataList;

    private Object mItemType;

    private ItemTypeUtil mUtil = new ItemTypeUtil();


    protected CommonRcvAdapter(@Nullable List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mDataList = data;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemType(Object)}
     *
     * 通过数据得到obj的类型的type
     * 然后，通过{@link ItemTypeUtil}来转换位int类型的type
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mItemType = getItemType(mDataList.get(position));
        return mUtil.getIntType(mItemType);
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, createItem(mItemType));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        debug((RcvAdapterItem) holder);
        ((RcvAdapterItem) holder).item.handleData(getConvertedData(mDataList.get(position), mItemType), position);
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内部用到的viewHold
    ///////////////////////////////////////////////////////////////////////////

    private static class RcvAdapterItem extends RecyclerView.ViewHolder {

        protected AdapterItem item;

        public boolean isNew = true; // debug中才用到

        protected RcvAdapterItem(Context context, ViewGroup parent, AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
            this.item = item;
            this.item.bindViews(itemView);
            this.item.setViews();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // For debug
    ///////////////////////////////////////////////////////////////////////////

    private void debug(RcvAdapterItem holder) {
        boolean debug = false;
        if (debug) {
            holder.itemView.setBackgroundColor(holder.isNew ? 0xffff0000 : 0xff00ff00);
            holder.isNew = false;
        }
    }

}
