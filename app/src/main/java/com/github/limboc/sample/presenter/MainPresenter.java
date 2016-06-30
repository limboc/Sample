package com.github.limboc.sample.presenter;

import android.content.Context;

import com.github.limboc.sample.api.DrakeetFactory;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.presenter.iview.IMainView;
import com.github.limboc.sample.ui.widget.LoadingDialog;
import com.github.limboc.sample.utils.L;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Chen on 2016/3/15.
 */
public class MainPresenter extends BasePresenter<IMainView>{

    private int page=1, limit = 10;
    private List<Meizhi> meizhiList = new ArrayList<>();

    public MainPresenter() {

    }

    public void loadData(){
        Subscription s = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(limit, page)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meizhiData -> {
                    if(page == 1){
                        meizhiList.clear();
                    }
                    if(meizhiData == null){
                        return;
                    }
                    meizhiList.addAll(meizhiData);
                    getView().onLoadDataSuccess(meizhiList);
                },throwable -> {
                    int size = meizhiList.size();
                    page = size % limit == 0 ? size/limit : size/limit+1;
                    handleError(throwable);
                });
        rxManager.add(s);
    }

    public void getMe(Context context){
        loadingDialog = new LoadingDialog(context, true);
        Subscription s = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(limit, page)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> loadingDialog.show())
                .subscribe(meizhiData -> {
                    loadingDialog.dismiss();
                    if(page == 1){
                        meizhiList.clear();
                    }
                    if(meizhiData == null){
                        return;
                    }
                    meizhiList.addAll(meizhiData);
                    getView().onLoadDataSuccess(meizhiList);
                },throwable -> {
                    loadingDialog.dismiss();
                    int size = meizhiList.size();
                    page = size % limit == 0 ? size/limit : size/limit+1;
                    handleError(throwable);
                }, ()-> loadingDialog.dismiss());
        loadingDialog.setOnCancelListener(l -> {
            if (!s.isUnsubscribed()) {
                s.unsubscribe();
            }
        });
        rxManager.add(s);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean hasNext(){
        int size = meizhiList.size();
        return (size != 0 && size % limit == 0) ? true:false;
    }

}
