package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.Character;
import com.github.limboc.sample.utils.L;
import com.github.limboc.sample.utils.RxBus;

import butterknife.Bind;

/**
 * Created by Chen on 2016/3/22.
 */
public class MovieActivity extends BaseActivity {
    @Bind(R.id.tvName)
    TextView tvName;

    @Override
    protected int getLayoutId() {
        return R.layout.item_hero;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {
        tvName.setOnClickListener(v -> RxBus.getDefault().post(new Character("hah", "url")));
    }

    @Override
    protected void initData() {

    }
}
