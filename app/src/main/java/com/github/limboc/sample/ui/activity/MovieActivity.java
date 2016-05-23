package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.SimpleResult;
import com.github.limboc.sample.presenter.BasePresenter;
import com.github.limboc.sample.ui.widget.DrawClickableTextView;
import com.github.limboc.sample.ui.widget.PasswordEditText;
import com.github.limboc.sample.utils.Event;
import com.github.limboc.sample.utils.L;
import com.github.limboc.sample.utils.RxBus;
import com.github.limboc.sample.utils.RxManager;

import butterknife.Bind;

/**
 * Created by Chen on 2016/3/22.
 */
public class MovieActivity extends BaseActivity<BasePresenter> {
    @Bind(R.id.tvName)
    DrawClickableTextView tvName;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_movie;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {

    }

    @Override
    protected void initData() {
        SimpleResult result = new SimpleResult<String>();
        result.setResults("123");
        tvName.setOnTextViewListener(isOpen -> {
            result.setResults(isOpen + "");
            presenter.post(Event.CLICK, "123456");
        });
    }

    @Override
    public void showMessage(String message) {

    }
}
