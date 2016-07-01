package com.github.limboc.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;


import com.github.limboc.sample.R;
import com.github.limboc.sample.presenter.BasePresenter;
import com.github.limboc.sample.presenter.iview.IBaseView;
import com.github.limboc.sample.utils.TUtil;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Chen on 2016/6/14.
 */
public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment implements IBaseView {

    T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = TUtil.getT(this, 0);
        presenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void handleThrowable(Throwable throwable) {
        if(throwable instanceof SocketException || throwable instanceof ConnectException
                || throwable instanceof UnknownHostException){
            com.github.limboc.sample.utils.T.showShort(R.string.connet_time_out);
        }else{
            com.github.limboc.sample.utils.T.showShort(throwable.getMessage());
        }

    }
}
