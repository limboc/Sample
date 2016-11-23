package com.github.limboc.sample.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.limboc.refresh.OnLoadMoreListener;
import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.item.LoadMoreItem;
import com.github.limboc.sample.utils.L;
import me.drakeet.multitype.ItemViewProvider;

/**
 * @author drakeet
 */
public class LoadMoreProvider
    extends ItemViewProvider<LoadMoreItem, LoadMoreProvider.ViewHolder> {
    private OnLoadMoreListener onLoadMoreListener;

    public LoadMoreProvider(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @NonNull @Override
    protected ViewHolder onCreateViewHolder(
        @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_load_more, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull
        LoadMoreItem loadMore) {
        holder.setData(loadMore);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private View loadingView;
        private View errorView;
        private View endView;
        private LoadMoreItem loadMoreItem;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            loadingView = itemView.findViewById(R.id.text_loadMoreListItem_loading);
            errorView = itemView.findViewById(R.id.text_loadMoreListItem_error);
            endView = itemView.findViewById(R.id.text_loadMoreListItem_end);
            errorView.setOnClickListener(view -> {
                loadMoreItem.setStatus(LoadMoreItem.PRELOADING);
                setData(loadMoreItem);
            });
        }


        void setData(@NonNull final LoadMoreItem loadMore) {
            this.loadMoreItem = loadMore;
            if(loadMore.getStatus().equals(LoadMoreItem.PRELOADING)){
                showLoading();
                loadMore.setStatus(LoadMoreItem.LOADING);
                setData(loadMore);
            }else if(loadMore.getStatus().equals(LoadMoreItem.LOADING)){
                showLoading();
                onLoadMoreListener.onLoadMore();
            }else if(loadMore.getStatus().equals(LoadMoreItem.ERROR)){
                showError();
            }else if(loadMore.getStatus().equals(LoadMoreItem.END)){
                showEnd();
            }
        }


        public void showLoading() {
            loadingView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        public void showError() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        public void showEnd() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.VISIBLE);
        }
    }
}
