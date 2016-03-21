package com.github.limboc.sample.ui.activity;

import android.os.Bundle;

import com.github.limboc.sample.ui.widget.patternlock.PatternView;
import com.github.limboc.sample.utils.PatternLockUtils;

import java.util.List;

/**
 * Created by Chen on 2016/3/14.
 */
public class SetPatternActivity extends com.github.limboc.sample.ui.widget.patternlock.SetPatternActivity{

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        PatternLockUtils.setPattern(pattern, this);
    }
}
