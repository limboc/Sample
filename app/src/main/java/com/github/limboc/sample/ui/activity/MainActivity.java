package com.github.limboc.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.limboc.refresh.OnRefreshListener;
import com.github.limboc.refresh.SwipeToLoadLayout;
import com.github.limboc.sample.DrakeetFactory;
import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.presenter.MainPresenter;
import com.github.limboc.sample.presenter.iview.IMainView;
import com.github.limboc.sample.ui.adapter.BaseRecyclerAdapter;
import com.github.limboc.sample.ui.item.ImgItem;
import com.github.limboc.sample.ui.item.LoadMoreRecyclerItemFactory;
import com.github.limboc.sample.ui.item.OnRecyclerLoadMoreListener;
import com.github.limboc.sample.ui.item.ViewPagerItem;
import com.github.limboc.sample.ui.widget.BottomSheetDialogView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements OnRefreshListener, OnRecyclerLoadMoreListener, IMainView {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private int page=1, limit = 10;
    private List<Object> objectList;
    private BaseRecyclerAdapter adapter;
    private ViewPagerItem viewPagerItem;
    private int mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeToLoadLayout.setOnRefreshListener(this);

        swipeToLoadLayout.post(() -> swipeToLoadLayout.setRefreshing(true));

        presenter = new MainPresenter();
        presenter.attachView(this);

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
        //loadData();
        presenter.setPage(1);
        presenter.loadData();

    }

    @Override
    public void onLoadMore(BaseRecyclerAdapter adapter) {
        page++;
        presenter.setPage(presenter.getPage()+1);
        presenter.loadData();
        //loadData();
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
            adapter = new BaseRecyclerAdapter(objectList);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_day_night_no:
                mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                return true;
            case R.id.action_day_night_yes:
                mDayNightMode = AppCompatDelegate.MODE_NIGHT_YES;
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                return true;
            case R.id.action_bottom_sheet_dialog:
                BottomSheetDialogView.show(this, mDayNightMode);
                return true;
            case R.id.action_confirm_dialog:
                showConfirmDialog(true, "hahhaha", (dialog, which) -> {

                });
                return true;
            case R.id.action_progress_dialog:
                startActivity(new Intent(context, SetPatternActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadDataSuccess(List<Object> objectList) {
        this.objectList = objectList;
        if(presenter.getPage() == 1){
            swipeToLoadLayout.setRefreshing(false);
            adapter = new BaseRecyclerAdapter(this.objectList);
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
        Log.d("TTTTT", "ddddd");
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    protected void onDestroy() {
        this.presenter.detachView();
        super.onDestroy();
    }
}
