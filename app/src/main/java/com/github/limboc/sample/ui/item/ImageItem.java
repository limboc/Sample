package com.github.limboc.sample.ui.item;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.Meizhi;
import com.squareup.picasso.Picasso;


public class ImageItem implements AdapterItem<Meizhi> {

    private static final String TAG = "ImageItem";
    private Context context;
    
    @Override
    public int getLayoutResId() {
        return R.layout.item_image;
    }

    ImageView iv_avatar;

    @Override
    public void bindViews(View root) {
        context = root.getContext();
        iv_avatar = (ImageView) root.findViewById(R.id.iv_avatar);
    }

    @Override
    public void setViews() {
        //Log.d(TextItem.class.getSimpleName(), "setViews--------->");
    }

    @Override
    public void handleData(Meizhi model, int position) {
        Picasso.with(context).load(model.getUrl()).resize(500,500).into(iv_avatar);
    }

}

