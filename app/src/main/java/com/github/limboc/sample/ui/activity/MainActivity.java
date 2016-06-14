package com.github.limboc.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.github.limboc.refresh.OnRefreshListener;
import com.github.limboc.refresh.SwipeToLoadLayout;
import com.github.limboc.sample.R;
import com.github.limboc.sample.presenter.MainPresenter;
import com.github.limboc.sample.presenter.iview.IMainView;
import com.github.limboc.sample.ui.adapter.BaseRecyclerAdapter;
import com.github.limboc.sample.ui.item.ImgItem;
import com.github.limboc.sample.ui.item.LoadMoreRecyclerItemFactory;
import com.github.limboc.sample.ui.item.OnRecyclerLoadMoreListener;
import com.github.limboc.sample.ui.item.ViewPagerItem;
import com.github.limboc.sample.ui.widget.BottomSheetDialogView;
import com.github.limboc.sample.ui.widget.MultipleStatusView;
import com.github.limboc.sample.ui.widget.progressdialog.ProgressSubscriber;
import com.github.limboc.sample.utils.Event;
import com.github.limboc.sample.utils.L;
import com.github.limboc.sample.utils.NetworkUtils;
import com.github.limboc.sample.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseLoadActivity<MainPresenter> implements OnRefreshListener, OnRecyclerLoadMoreListener, IMainView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.swipe_target)
    RecyclerView recyclerView;
    @Bind(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private List<Object> objectList;
    private BaseRecyclerAdapter adapter;
    private ViewPagerItem viewPagerItem;
    private int mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {
        setSupportActionBar(toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeToLoadLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {

        multipleStatusView.setOnRetryClickListener(l-> onRefresh());
        multipleStatusView.showLoading();
        onRefresh();
        presenter.on(Event.CLICK, (object) -> L.d("Main", object.toString()));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewPagerItem != null) {
            viewPagerItem.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (viewPagerItem != null) {
            viewPagerItem.stop();
        }

    }

    @Override
    public void onRefresh() {
        if(!NetworkUtils.isConnected(context)){
            multipleStatusView.showNoNetwork();
            return;
        }
        if (adapter != null) {
            adapter.loadMoreFinished();
        }
        objectList = new ArrayList<>();
        presenter.setPage(1);
        presenter.loadData();

    }

    @Override
    public void onLoadMore(BaseRecyclerAdapter adapter) {
        if (swipeToLoadLayout.isRefreshing() && adapter != null) {
            swipeToLoadLayout.setRefreshing(false);
            adapter.loadMoreFinished();
            return;
        }
        presenter.setPage(presenter.getPage() + 1);
        presenter.loadData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                BottomSheetDialogView
                        .show(this, mDayNightMode)
                        .setOnItemClickListener(position -> {
                            T.showShort(position + "");
                        });
                return true;
            case R.id.action_confirm_dialog:
                showConfirmDialog(true, "hahhaha", (dialog, which) -> {

                });
                return true;
            case R.id.action_progress_dialog:
                presenter.getMe(new ProgressSubscriber(context, o -> {
                    //L.d("main", "load completed");
                    L.d("main", o.toString());
                }));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadDataSuccess(List<Object> list) {
        objectList = list;
        if (presenter.getPage() == 1) {
            swipeToLoadLayout.setRefreshing(false);
            adapter = new BaseRecyclerAdapter(this.objectList);
            viewPagerItem = new ViewPagerItem(getBaseContext());
            ImgItem imgItem = new ImgItem(getBaseContext());
            imgItem.setOnItemClickListener(position -> startActivity(new Intent(context, MovieActivity.class)));
            adapter.addItemFactory(imgItem);
            if (presenter.hasNext()) {
                adapter.enableLoadMore(new LoadMoreRecyclerItemFactory(this));
            }
            recyclerView.setAdapter(adapter);
        } else {
            if (!presenter.hasNext()) {
                adapter.loadMoreEnd();
            }
            adapter.loadMoreFinished();
            adapter.notifyDataSetChanged();
        }
        multipleStatusView.showContent();
    }


    @Override
    public void handleThrowable(Throwable throwable) {
        super.handleThrowable(throwable);
        if(objectList.size() == 0){
            multipleStatusView.showError();
        }
        swipeToLoadLayout.setRefreshing(false);
        if (adapter != null) {
            adapter.loadMoreFailed();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
