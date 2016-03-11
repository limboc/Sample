package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.limboc.refresh.OnRefreshListener;
import com.github.limboc.refresh.SwipeToLoadLayout;
import com.github.limboc.sample.DrakeetFactory;
import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.ui.adapter.AssemblyRecyclerAdapter;
import com.github.limboc.sample.ui.item.ImgItem;
import com.github.limboc.sample.ui.item.LoadMoreRecyclerItemFactory;
import com.github.limboc.sample.ui.item.OnItemClickListener;
import com.github.limboc.sample.ui.item.OnRecyclerLoadMoreListener;
import com.github.limboc.sample.ui.item.ViewPagerItem;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements OnRefreshListener, OnRecyclerLoadMoreListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private int page=1, limit = 10;
    private List<Object> objectList;
    private AssemblyRecyclerAdapter adapter;
    private ViewPagerItem viewPagerItem;

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

        swipeToLoadLayout.post(() -> swipeToLoadLayout.setRefreshing(true));

    }


    @Override
    public void onResume() {
        super.onResume();
        if(viewPagerItem != null){
            viewPagerItem.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if(viewPagerItem != null){
            viewPagerItem.stop();
        }

    }

    @Override
    public void onRefresh() {
        page=1;
        objectList = new ArrayList<>();
        loadData();

    }



    private void loadData(){
        Subscription s = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .finallyDo(()-> {
                    if(page == 1){
                        swipeToLoadLayout.setRefreshing(false);
                    }
                })
                .subscribe(meizhiData -> {
                    if(limit < meizhiData.getResults().size()){
                        adapter.loadMoreEnd();
                    }
                    if(page == 1){
                        objectList.add(meizhiData);
                    }
                    for(Meizhi item:meizhiData.getResults()){
                        objectList.add(item);
                    }
                    setAdapter();
                },throwable -> throwable.printStackTrace());
        addSubscription(s);
    }

    private void setAdapter() {
        if(page == 1){
            adapter = new AssemblyRecyclerAdapter(objectList);
            viewPagerItem = new ViewPagerItem(getBaseContext());
            adapter.addItemFactory(viewPagerItem);
            ImgItem imgItem = new ImgItem(getBaseContext());
            imgItem.setOnItemClickListener(position -> {
                Log.d("Main", position + "");
            });
            adapter.addItemFactory(imgItem);
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
