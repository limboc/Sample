package com.github.limboc.sample.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.limboc.sample.R;
import com.github.limboc.sample.ui.adapter.AbstractLoadMoreRecyclerItemFactory;


public class LoadMoreRecyclerItemFactory extends AbstractLoadMoreRecyclerItemFactory {

    public LoadMoreRecyclerItemFactory(OnRecyclerLoadMoreListener eventListener) {
        super(eventListener);
    }

    @Override
    public AbstractLoadMoreRecyclerItem createAssemblyItem(ViewGroup parent) {
        return new LoadMoreRecyclerItem(parent, this);
    }

    public static class LoadMoreRecyclerItem extends AbstractLoadMoreRecyclerItem {
        private View loadingView;
        private View errorView;
        private View endView;

        protected LoadMoreRecyclerItem(ViewGroup parent, AbstractLoadMoreRecyclerItemFactory baseFactory) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false), baseFactory);
        }

        @Override
        protected void onFindViews(View convertView) {
            loadingView = convertView.findViewById(R.id.text_loadMoreListItem_loading);
            errorView = convertView.findViewById(R.id.text_loadMoreListItem_error);
            endView = convertView.findViewById(R.id.text_loadMoreListItem_end);
        }

        @Override
        public View getErrorRetryView() {
            return errorView;
        }

        @Override
        public void showLoading() {
            loadingView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void showErrorRetry() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.VISIBLE);
            endView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void showEnd() {
            loadingView.setVisibility(View.INVISIBLE);
            errorView.setVisibility(View.INVISIBLE);
            endView.setVisibility(View.VISIBLE);
        }
    }
}
