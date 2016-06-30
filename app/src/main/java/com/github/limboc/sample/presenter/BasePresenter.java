package com.github.limboc.sample.presenter;

import com.github.limboc.sample.api.ApiException;
import com.github.limboc.sample.api.DrakeetFactory;
import com.github.limboc.sample.api.GankApi;
import com.github.limboc.sample.data.SimpleResult;
import com.github.limboc.sample.presenter.iview.IBaseView;
import com.github.limboc.sample.ui.widget.LoadingDialog;
import com.github.limboc.sample.utils.RxManager;
import rx.functions.Action1;
import rx.functions.Func1;


public class BasePresenter<T extends IBaseView> implements IPresenter<T> {

    private T mView;
    public GankApi mDataManager;
    public RxManager rxManager;
    public LoadingDialog loadingDialog;

    @Override
    public void attachView(T mvpView) {
        this.mView = mvpView;
        this.mDataManager = DrakeetFactory.getGankIOSingleton();
        this.rxManager = new RxManager();
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mDataManager = null;
        this.rxManager.clear();
        if(loadingDialog != null){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public void post(Object tag, Object content) {
        rxManager.post(tag, content);
    }

    public void on(String eventName, Action1<Object> action1) {
        rxManager.on(eventName, action1);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    public class HttpResultFunc<T> implements Func1<SimpleResult<T>, T> {

        @Override
        public T call(SimpleResult<T> simpleResult) {
            if (simpleResult.getResultCode() == -1) {
                throw new ApiException(100);
            }
            return simpleResult.getResults();
        }
    }

    public void handleError(Throwable throwable){
        throwable.printStackTrace();
        getView().handleThrowable(throwable);
    }
}
