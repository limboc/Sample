package com.github.limboc.sample.presenter;

import com.github.limboc.sample.presenter.iview.IBaseView;

/**
 * Created by Chen on 2016/3/15.
 */
public interface IPresenter<V extends IBaseView> {

    void attachView(V mvpView);

    void detachView();
}
