package com.github.limboc.sample.ui.item;


import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.MeizhiData;
import com.github.limboc.sample.data.bean.Character;
import com.github.limboc.sample.data.bean.DemoModel;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.data.bean.SectionCharacters;
import com.github.limboc.sample.ui.adapter.LoopViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewpagerItem implements AdapterItem<MeizhiData> {

    private static final String TAG = "ViewpagerItem";
    
    @Override
    public int getLayoutResId() {
        return R.layout.layout_viewpager;
    }

    ViewPager viewPager;
    ViewGroup indicators;
    LoopViewPagerAdapter mPagerAdapter;

    @Override
    public void bindViews(View root) {
        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        indicators = (ViewGroup) root.findViewById(R.id.indicators);

    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(MeizhiData item, int position) {
        if(viewPager.getAdapter() == null){
            mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
            viewPager.setAdapter(mPagerAdapter);
            viewPager.addOnPageChangeListener(mPagerAdapter);
            mPagerAdapter.setList(item.getResults());
        }else{
            mPagerAdapter.setList(item.getResults());
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

