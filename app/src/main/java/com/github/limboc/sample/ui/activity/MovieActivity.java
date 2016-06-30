package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.SimpleResult;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.presenter.BasePresenter;
import com.github.limboc.sample.ui.adapter.BaseRecyclerAdapter;
import com.github.limboc.sample.ui.item.ImgItem;
import com.github.limboc.sample.ui.widget.DrawClickableTextView;
import com.github.limboc.sample.utils.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Chen on 2016/3/22.
 */
public class MovieActivity extends BasePresenterActivity<BasePresenter> {
    @Bind(R.id.tvName)
    DrawClickableTextView tvName;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private BaseRecyclerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_movie;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        Gson gson = new Gson();
        List<Object> objectList = gson.fromJson(getIntent().getStringExtra("objectList"), new TypeToken<List<Meizhi>>(){}.getType()) ;
        adapter = new BaseRecyclerAdapter(objectList);
        adapter.addItemFactory(new ImgItem(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        SimpleResult result = new SimpleResult<String>();
        result.setResults("123");
        tvName.setOnTextViewListener(isOpen -> {
            result.setResults(isOpen + "");
            presenter.post(Event.CLICK, "123456");
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
