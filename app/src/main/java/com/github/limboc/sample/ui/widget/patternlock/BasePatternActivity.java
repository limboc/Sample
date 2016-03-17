package com.github.limboc.sample.ui.widget.patternlock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.activity.BaseActivity;

import butterknife.Bind;


public class BasePatternActivity extends BaseActivity {

    private static final int CLEAR_PATTERN_DELAY_MILLI = 2000;

    @Bind(R.id.pl_message_text)
    TextView mMessageText;
    @Bind(R.id.pl_pattern)
    PatternView mPatternView;
    @Bind(R.id.pl_button_container)
    LinearLayout mButtonContainer;
    @Bind(R.id.pl_left_button)
    Button mLeftButton;
    @Bind(R.id.pl_right_button)
    Button mRightButton;

    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            mPatternView.clearPattern();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.pl_base_pattern_activity;
    }

    @Override
    protected void initView(Bundle saveInstanceState) {

    }

    @Override
    protected void initData() {

    }


    protected void removeClearPatternRunnable() {
        mPatternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        mPatternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);
    }
}
