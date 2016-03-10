package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.limboc.refresh.OnLoadMoreListener;
import com.github.limboc.refresh.OnRefreshListener;
import com.github.limboc.refresh.SwipeToLoadLayout;
import com.github.limboc.sample.DrakeetFactory;
import com.github.limboc.sample.R;
import com.github.limboc.sample.data.MeizhiData;
import com.github.limboc.sample.data.bean.DemoModel;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.ui.adapter.AssemblyRecyclerAdapter;
import com.github.limboc.sample.ui.adapter.CommonRcvAdapter;
import com.github.limboc.sample.ui.item.AdapterItem;
import com.github.limboc.sample.ui.item.ImageItem;
import com.github.limboc.sample.ui.item.ImgItem;
import com.github.limboc.sample.ui.item.LoadMoreRecyclerItemFactory;
import com.github.limboc.sample.ui.item.OnRecyclerLoadMoreListener;
import com.github.limboc.sample.ui.item.ViewpagerItem;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestActivity extends BaseActivity implements OnRefreshListener, OnRecyclerLoadMoreListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private int page=1, limit = 10;
    private List<Meizhi> list;
    private AssemblyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeToLoadLayout.setOnRefreshListener(this);


        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }

    }

    @Override
    public void onRefresh() {
        page=1;
        list = new ArrayList<>();
        loadData();

    }



    private void loadData(){
        Subscription s = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .finallyDo(()-> {
                    if(page == 1){
                        swipeToLoadLayout.setRefreshing(false);
                    }
                })
                .subscribe(meizhiData -> {
                    list.addAll(meizhiData.getResults());
                    setAdapter();
                },throwable -> throwable.printStackTrace());
        addSubscription(s);
    }

    private void setAdapter() {
        if(page == 1){
            adapter = new AssemblyRecyclerAdapter(list);
            adapter.addItemFactory(new ImgItem(getBaseContext()));
            adapter.enableLoadMore(new LoadMoreRecyclerItemFactory(this));
            recyclerView.setAdapter(adapter);
        }else{
            adapter.loadMoreFinished();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadMore(AssemblyRecyclerAdapter adapter) {
        page++;
        loadData();
    }
}
