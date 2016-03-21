package com.github.limboc.sample.ui.activity;

import android.content.Intent;

import com.github.limboc.sample.ui.widget.patternlock.PatternView;
import com.github.limboc.sample.utils.PatternLockUtils;
import com.github.limboc.sample.utils.PreferenceContract;
import com.github.limboc.sample.utils.PreferenceUtils;

import java.util.List;


public class ConfirmPatternActivity extends com.github.limboc.sample.ui.widget.patternlock.ConfirmPatternActivity {



    @Override
    protected boolean isStealthModeEnabled() {
        return !PreferenceUtils.getBoolean(PreferenceContract.KEY_PATTERN_VISIBLE,
                PreferenceContract.DEFAULT_PATTERN_VISIBLE, this);
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        return PatternLockUtils.isPatternCorrect(pattern, this);
    }

    @Override
    protected void onForgotPassword() {

        startActivity(new Intent(this, ResetPatternActivity.class));
        super.onForgotPassword();
    }
}
