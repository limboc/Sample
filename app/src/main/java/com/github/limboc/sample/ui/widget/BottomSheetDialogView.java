package com.github.limboc.sample.ui.widget;


import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.item.OnItemClickListener;
import com.github.limboc.sample.utils.T;

public class BottomSheetDialogView {


    private static String[] sStringList;
    private BottomSheetDialog dialog;
    private OnItemClickListener listener;

    static {
        sStringList = new String[10];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sStringList.length; i++) {
            stringBuilder.append(i + 1);
            sStringList[i] = stringBuilder.toString();
        }
    }

    /**
     * remember to call setLocalNightMode for dialog
     *
     * @param context
     * @param dayNightMode current day night mode
     */
    public BottomSheetDialogView(Context context, int dayNightMode) {
        dialog = new BottomSheetDialog(context);
        dialog.getDelegate().setLocalNightMode(dayNightMode);

        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog_recycler_view, null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SimpleAdapter());

        dialog.setContentView(view);
        dialog.show();
    }

    public static BottomSheetDialogView show(Context context, int dayNightMode) {
        return new BottomSheetDialogView(context, dayNightMode);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    private class SimpleAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(sStringList[position]);
            holder.mTextView.setOnClickListener(v->{
                dialog.dismiss();
                if(listener != null){
                    listener.onClick(position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return sStringList.length;
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
