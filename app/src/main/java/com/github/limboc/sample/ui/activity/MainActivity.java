package com.github.limboc.sample.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.github.limboc.sample.ui.adapter.CommonRcvAdapter;
import com.github.limboc.sample.ui.item.AdapterItem;
import com.github.limboc.sample.ui.item.ButtonItem;
import com.github.limboc.sample.ui.item.TextItem;
import com.github.limboc.sample.ui.item.ViewpagerItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener{

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private List<DemoModel> data;
    private int page=0, limit = 10;
    private ViewpagerItem viewpagerItem;

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
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

        viewpagerItem = new ViewpagerItem();
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
        // TODO: 2016/3/2 stop viewpager 
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
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page=0;
                data = new ArrayList<>();
                loadData(getBaseContext());
                swipeToLoadLayout.setRefreshing(false);
                recyclerView.setAdapter(getAdapter(data));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, 3000);

    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                loadData(getBaseContext());
                swipeToLoadLayout.setLoadingMore(false);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, 3000);

    }


    private CommonRcvAdapter<DemoModel> getAdapter(List<DemoModel> data) {
        return new CommonRcvAdapter<DemoModel>(data) {

            @Override
            public Object getItemType(DemoModel demoModel) {
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                switch (((String) type)) {
                    case "viewpager":
                        return viewpagerItem;
                    case "text":
                        return new TextItem();
                    case "button":
                        return new ButtonItem();
                    default:
                        throw new IllegalArgumentException("不合法的type");
                }
            }
        };
    }

    public void loadData(Context context) {
        List<String> originList = Arrays.asList(context.getResources().getStringArray(R.array.country_names));
        if(data != null && data.size() == 0){
            DemoModel model0 = new DemoModel();
            model0.type = "viewpager";
            model0.content = "";
            data.add(model0);
        }
        for (int i = page*limit; i < limit + page*limit; i++) {
            int type = (int) (Math.random() * 2);
            //Log.d(TAG, "type = " + type);
            DemoModel model = new DemoModel();
            switch (type) {
                case 0:
                    model.type = "text";
                    model.content = originList.get(i);
                    break;
                case 1:
                    model.type = "button";
                    model.content = "b:" + originList.get(i);
                    break;
                default:
            }
            data.add(model);
        }

        loadData();
    }

    private void loadData(){
        DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meizhiData -> Log.d("main", meizhiData.toString()),
                        throwable -> throwable.printStackTrace());
    }


}
