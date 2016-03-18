package com.github.limboc.sample.presenter;

import com.github.limboc.sample.DrakeetFactory;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.presenter.iview.IMainView;

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
    private List<Object> objectList;

    public MainPresenter() {

    }

    public void loadData(){
        if(page == 1){
            objectList = new ArrayList<>();
        }
        Subscription s = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(limit, page)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .finallyDo(()-> {

                })
                .subscribe(meizhiData -> {
                    if(meizhiData == null){
                        return;
                    }
                    for(Meizhi item:meizhiData){
                        objectList.add(item);
                    }
                    getView().onLoadDataSuccess(objectList);
                },throwable -> {
                    handleError(throwable);
                });
        mCompositeSubscription.add(s);
    }

    public void getMe(Subscriber<List<Meizhi>> subscriber){
        Observable observable = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(limit, page)
                .map(new HttpResultFunc<>());

        toSubscribe(observable, subscriber);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

}
