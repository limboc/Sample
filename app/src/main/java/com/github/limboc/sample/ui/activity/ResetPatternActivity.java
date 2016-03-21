package com.github.limboc.sample.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.github.limboc.sample.R;
import com.github.limboc.sample.utils.PatternLockUtils;

import butterknife.Bind;


public class ResetPatternActivity extends BaseActivity {

    @Bind(R.id.ok_button)
    Button mOkButton;
    @Bind(R.id.cancel_button)
    Button mCancelButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pattern;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {
        mOkButton.setOnClickListener(v -> {
            PatternLockUtils.clearPattern(ResetPatternActivity.this);
            finish();
        });

        mCancelButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {

    }
}
