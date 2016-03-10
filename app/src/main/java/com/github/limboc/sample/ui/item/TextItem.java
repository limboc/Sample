package com.github.limboc.sample.ui.item;


import android.view.View;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.data.bean.DemoModel;



public class TextItem implements AdapterItem<DemoModel> {

    private static final String TAG = "TextItem";
    
    @Override
    public int getLayoutResId() {
        return R.layout.item_text;
    }

    TextView textView;

    @Override
    public void bindViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);
    }

    @Override
    public void setViews() {
        //Log.d(TextItem.class.getSimpleName(), "setViews--------->");
    }

    @Override
    public void handleData(DemoModel model, int position) {
        textView.setText((String)model.getContent());
    }

}

