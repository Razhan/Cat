package com.ran.delta.presentation.ice;

import android.support.v4.widget.SwipeRefreshLayout;

import com.ran.delta.presentation.MvpPresenter;

public abstract class MvpLceActivityWithRefresh<M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpLceActivity<SwipeRefreshLayout, M, V, P>
        implements SwipeRefreshLayout.OnRefreshListener {


    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

}
