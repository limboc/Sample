package com.github.limboc.sample.presenter;

import com.github.limboc.sample.DrakeetFactory;
import com.github.limboc.sample.data.bean.Meizhi;
import com.github.limboc.sample.presenter.iview.IMainView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Chen on 2016/3/15.
 */
public class MainPresenter extends BasePresenter<IMainView>{

    private int page=1, limit = 10;
    private List<Object> objectList;

    public void loadData(){
        if(page == 1){
            objectList = new ArrayList<>();
        }
        Subscription s = DrakeetFactory.getGankIOSingleton()
                .getMeizhiData(limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .finallyDo(()-> {

                })
                .subscribe(meizhiData -> {
                    if(page == 1){
                        objectList.add(meizhiData);
                    }
                    for(Meizhi item:meizhiData.getResults()){
                        objectList.add(item);
                    }
                    getMvpView().onLoadDataSuccess(objectList);
                },throwable -> throwable.printStackTrace());
        mCompositeSubscription.add(s);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
