package com.github.limboc.sample.ui.widget.patternlock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.activity.BaseActivity;


public class BasePatternActivity extends BaseActivity {

    private static final int CLEAR_PATTERN_DELAY_MILLI = 2000;

    protected TextView mMessageText;
    protected PatternView mPatternView;
    protected LinearLayout mButtonContainer;
    protected Button mLeftButton;
    protected Button mRightButton;

    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            mPatternView.clearPattern();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pl_base_pattern_activity);
        mMessageText = (TextView)findViewById(R.id.pl_message_text);
        mPatternView = (PatternView)findViewById(R.id.pl_pattern);
        mButtonContainer = (LinearLayout)findViewById(R.id.pl_button_container);
        mLeftButton = (Button)findViewById(R.id.pl_left_button);
        mRightButton = (Button)findViewById(R.id.pl_right_button);
    }

    protected void removeClearPatternRunnable() {
        mPatternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        mPatternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);
    }
}
