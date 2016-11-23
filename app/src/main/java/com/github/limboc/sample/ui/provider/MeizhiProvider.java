package com.github.limboc.sample.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.Meizhi;
import com.squareup.picasso.Picasso;
import me.drakeet.multitype.ItemViewProvider;

/**
 * @author drakeet
 */
public class MeizhiProvider
    extends ItemViewProvider<Meizhi, MeizhiProvider.ViewHolder> {

    @NonNull @Override
    protected ViewHolder onCreateViewHolder(
        @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull
        Meizhi meizhi) {
        holder.setData(meizhi);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_avatar)
        ImageView iv_avatar;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void setData(@NonNull final Meizhi data) {
            Picasso.with(itemView.getContext()).load(data.getUrl()).resize(500,400).centerCrop().into(iv_avatar);
        }


    }
}
