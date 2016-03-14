package com.github.limboc.sample.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.utils.LoadingDialog;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class BaseActivity extends AppCompatActivity {


    private CompositeSubscription mCompositeSubscription;
    LoadingDialog loadingDialog;
    AlertDialog.Builder confirmDialog;
    Context context;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

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
