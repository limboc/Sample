package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.limboc.refresh.OnLoadMoreListener;
import com.github.limboc.refresh.OnRefreshListener;
import com.github.limboc.refresh.SwipeToLoadLayout;
import com.github.limboc.sample.App;
import com.github.limboc.sample.R;
import com.github.limboc.sample.bean.SectionCharacters;
import com.github.limboc.sample.ui.adapter.RecyclerCharactersAdapter;
import com.github.limboc.sample.utils.Constants;
import com.github.limboc.sample.utils.GsonRequest;

public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener{

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private RecyclerCharactersAdapter mAdapter;
    private int mPageNum, mType;
    private String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        mAdapter = new RecyclerCharactersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
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
        mAdapter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        App.getRequestQueue().cancelAll(TAG + "refresh" + mType);
        App.getRequestQueue().cancelAll(TAG + "loadmore" + mType);
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
        mAdapter.stop();
    }

    @Override
    public void onRefresh() {
        GsonRequest request = new GsonRequest<SectionCharacters>(Constants.API.CHARACTERS, SectionCharacters.class, new Response.Listener<SectionCharacters>() {
            @Override
            public void onResponse(final SectionCharacters characters) {
                Log.d("response", characters.toString());
                // here, I use post delay to show more animation, you don't have to.
                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPageNum = 0;
                        mAdapter.setList(characters.getCharacters(), characters.getSections().subList(0, mPageNum + 1));
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeToLoadLayout.setRefreshing(false);
                volleyError.printStackTrace();
            }
        });
        App.getRequestQueue().add(request).setTag(TAG + "refresh" + mType);
    }

    @Override
    public void onLoadMore() {
        GsonRequest request = new GsonRequest<SectionCharacters>(Constants.API.CHARACTERS, SectionCharacters.class, new Response.Listener<SectionCharacters>() {
            @Override
            public void onResponse(final SectionCharacters characters) {
                // here, I use post delay to show more animation, you don't have to.
                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPageNum < 3) {
                            mPageNum++;
                            mAdapter.append(characters.getSections().subList(mPageNum, mPageNum + 1));
                        } else {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                }, 2000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeToLoadLayout.setLoadingMore(false);
                volleyError.printStackTrace();
            }
        });
        App.getRequestQueue().add(request).setTag(TAG + "loadmore" + mType);
    }
}
