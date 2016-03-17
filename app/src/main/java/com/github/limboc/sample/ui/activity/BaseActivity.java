package com.github.limboc.sample.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.widget.LoadingDialog;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {


    LoadingDialog loadingDialog;
    AlertDialog.Builder confirmDialog;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        context = this;
        ButterKnife.bind(this);
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle saveInstanceState);

    protected abstract void initData();

    public void showLoadingDialog(boolean isCancelable){
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(context, isCancelable);
        }
        if(!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    public void hideLoadingDialog(){
        if(loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public void showConfirmDialog(boolean isCancelable, String message, DialogInterface.OnClickListener onClickListener){
        if(confirmDialog == null){
            confirmDialog = new AlertDialog.Builder(context)
                    .setPositiveButton(R.string.ok, onClickListener)
                    .setNegativeButton(R.string.cancel, null)
                    .setCancelable(isCancelable)
                    .setMessage(message);
        }
        confirmDialog.show();
    }

}
