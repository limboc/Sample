package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.limboc.refresh.OnLoadMoreListener;
import com.github.limboc.refresh.OnRefreshListener;
import com.github.limboc.refresh.SwipeToLoadLayout;
import com.github.limboc.sample.DrakeetFactory;
import com.github.limboc.sample.R;
import com.github.limboc.sample.data.MeizhiData;
import com.github.limboc.sample.data.bean.DemoModel;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.ui.adapter.CommonRcvAdapter;
import com.github.limboc.sample.ui.item.AdapterItem;
import com.github.limboc.sample.ui.item.ImageItem;
import com.github.limboc.sample.ui.item.ViewpagerItem;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener{

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private MeizhiData data;
    private int page=1, limit = 10;
    private ViewpagerItem viewpagerItem;
    private List<DemoModel> objectList;

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
        swipeToLoadLayout.setOnLoadMoreListener(this);
        /*recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });*/


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
        if(viewpagerItem != null){
            viewpagerItem.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
        if(viewpagerItem != null){
            viewpagerItem.stop();
        }
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(()-> {
                page=1;
                data = new MeizhiData();
                objectList = new ArrayList<>();
                loadData();
        }, 3000);

    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(()-> {
                page++;
                loadData();
        }, 3000);

    }


    private void loadData(){
        Subscription s = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .finallyDo(()-> {
                    if(page == 1){
                        swipeToLoadLayout.setRefreshing(false);
                    }else{
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                })
                .subscribe(meizhiData -> {
                    data = meizhiData;
                    setAdapter();
                },throwable -> throwable.printStackTrace());
        addSubscription(s);
    }

    private void setAdapter(){
        if(page == 1){
            DemoModel model = new DemoModel();
            model.setType("viewpager");
            model.setContent(data);
            objectList.add(model);

            for(Meizhi item:data.getResults()){
                DemoModel m = new DemoModel();
                m.setType("image");
                m.setContent(item);
                objectList.add(m);
            }

            recyclerView.setAdapter(new CommonRcvAdapter<DemoModel>(objectList) {

                @Override
                public Object getItemType(DemoModel demoModel) {
                    return demoModel.getType();
                }

                @NonNull
                @Override
                public AdapterItem createItem(Object type) {
                    switch ((String)type){
                        case "viewpager":
                            viewpagerItem = new ViewpagerItem();
                            return viewpagerItem;
                        case "image":
                            return new ImageItem();
                        default:
                            throw new IllegalArgumentException("不合法的type");
                    }

                }

                @NonNull
                @Override
                public Object getConvertedData(DemoModel data, Object type) {
                    switch ((String)type){
                        case "viewpager":
                            return (MeizhiData)data.getContent();
                        case "image":
                            return (Meizhi)data.getContent();
                        default:
                            throw new IllegalArgumentException("不合法的type");
                    }
                }
            });

        }
        recyclerView.getAdapter().notifyDataSetChanged();

    }


}
