package com.github.limboc.sample.presenter;

import com.github.limboc.sample.DrakeetFactory;
import com.github.limboc.sample.GankApi;
import com.github.limboc.sample.presenter.iview.IBaseView;

import rx.subscriptions.CompositeSubscription;


public class BasePresenter<T extends IBaseView> implements IPresenter<T> {

    private T mMvpView;
    public CompositeSubscription mCompositeSubscription;
    public GankApi mDataManager;


    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
        this.mCompositeSubscription = new CompositeSubscription();
        this.mDataManager = DrakeetFactory.getGankIOSingleton();
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
        this.mDataManager = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

}
