package com.github.limboc.sample.ui.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.Meizhi;
import com.squareup.picasso.Picasso;


public class ImgItem extends AssemblyRecyclerItemFactory<ImgItem.ImgRecyclerItem> {

    Context context;

    public ImgItem(Context context) {
        this.context = context;
    }

    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof Meizhi;
    }

    @Override
    public ImgRecyclerItem createAssemblyItem(ViewGroup parent) {
        return new ImgRecyclerItem(parent, this);
    }



    public class ImgRecyclerItem extends AssemblyRecyclerItem<Meizhi, ImgItem> {

        private ImageView iv_avatar;

        protected ImgRecyclerItem(ViewGroup parent, ImgItem factory) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false), factory);
        }

        @Override
        protected void onFindViews(View convertView) {
            iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        }

        @Override
        protected void onConfigViews(final Context context) {

        }

        @Override
        protected void onSetData(int position, Meizhi item) {

            Picasso.with(context).load(item.getUrl()).resize(500,400).centerCrop().into(iv_avatar);
        }

    }


}
