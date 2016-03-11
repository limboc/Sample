package com.github.limboc.sample.ui.item;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.MeizhiData;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.ui.adapter.LoopViewPagerAdapter;
import com.squareup.picasso.Picasso;


public class ViewPagerItem extends AssemblyRecyclerItemFactory<ViewPagerItem.ViewPagerRecyclerItem> {

    Context context;
    LoopViewPagerAdapter mPagerAdapter;

    public ViewPagerItem(Context context) {
        this.context = context;
    }

    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof MeizhiData;
    }

    @Override
    public ViewPagerRecyclerItem createAssemblyItem(ViewGroup parent) {
        return new ViewPagerRecyclerItem(parent, this);
    }



    public class ViewPagerRecyclerItem extends AssemblyRecyclerItem<MeizhiData, ViewPagerItem> {

        private ViewPager viewPager;
        ViewGroup indicators;

        protected ViewPagerRecyclerItem(ViewGroup parent, ViewPagerItem factory) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_viewpager, parent, false), factory);
        }

        @Override
        protected void onFindViews(View convertView) {
            viewPager = (ViewPager) convertView.findViewById(R.id.viewPager);
            indicators = (ViewGroup) convertView.findViewById(R.id.indicators);
        }

        @Override
        protected void onConfigViews(final Context context) {


        }

        @Override
        protected void onSetData(int position, MeizhiData item) {
            if(viewPager.getAdapter() == null){
                mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
                viewPager.setAdapter(mPagerAdapter);
                viewPager.addOnPageChangeListener(mPagerAdapter);
                mPagerAdapter.setList(item.getResults());
            }else{
                mPagerAdapter.setList(item.getResults());
            }

        }

    }

    public void start() {
        if (mPagerAdapter != null) {
            mPagerAdapter.start();
        }
    }

    public void stop() {
        if (mPagerAdapter != null) {
            mPagerAdapter.stop();
        }
    }


}
