package com.github.limboc.sample.ui.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.utils.DensityUtils;
import com.github.limboc.sample.utils.L;


public class LoadingDialog extends Dialog {
    Context context;
    boolean isCancelable;

    public LoadingDialog(Context context, boolean isCancelable) {
        super(context, R.style.DialogLoading);
        this.context = context;
        this.isCancelable = isCancelable;
    }

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.PROGRESS_VISIBILITY_ON);
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                DensityUtils.dp2px(context, 100),
                DensityUtils.dp2px(context, 80));
        lp.gravity = Gravity.CENTER;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_custom_progress, null);

        addContentView(view, lp);
        setCanceledOnTouchOutside(isCancelable);
        setCancelable(isCancelable);
    }

}
