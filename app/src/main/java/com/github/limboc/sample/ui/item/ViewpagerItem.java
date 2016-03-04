package com.github.limboc.sample.ui.item;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.Character;
import com.github.limboc.sample.data.bean.DemoModel;
import com.github.limboc.sample.data.bean.SectionCharacters;
import com.github.limboc.sample.ui.adapter.LoopViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewpagerItem implements AdapterItem<DemoModel> {

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
        //Log.d(TextItem.class.getSimpleName(), "setViews--------->");
    }

    @Override
    public void handleData(DemoModel item, int position) {
        /*if(model.getCharacters() == null){
            return;
        }*/
        SectionCharacters model = new SectionCharacters();
        List<Character> list = new ArrayList<>();
        list.add(new Character("Ant-Man", "http://i.annihil.us/u/prod/marvel/i/mg/9/a0/54adb647b792d.png"));
        list.add(new Character("Black Panther", "http://x.annihil.us/u/prod/marvel/i/mg/c/00/54adb7c4e163b.png"));
        model.setCharacters(list);
        if(viewPager.getAdapter() == null){
            mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
            viewPager.setAdapter(mPagerAdapter);
            viewPager.addOnPageChangeListener(mPagerAdapter);
            mPagerAdapter.setList(model.getCharacters());
        }else{
            mPagerAdapter.setList(model.getCharacters());
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

