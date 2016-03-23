package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.SimpleResult;
import com.github.limboc.sample.ui.widget.DrawClickableTextView;
import com.github.limboc.sample.utils.RxBus;

import butterknife.Bind;

/**
 * Created by Chen on 2016/3/22.
 */
public class MovieActivity extends BaseActivity {
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
            RxBus.getDefault().post(result);
        });
    }
}
