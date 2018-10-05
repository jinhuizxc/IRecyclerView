package com.jinhui.library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by jh on 2018/10/4.
 * Email: 1004260403@qq.com
 *
 * 加载更多的抽象类
 */
public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        Log.e("加载更多的抽象类", "onScrollStateChanged");
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        assert layoutManager != null;
        int visibleItemCount = layoutManager.getChildCount();

        boolean triggerCondition = visibleItemCount > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && canTriggerLoadMore(recyclerView);
        if (triggerCondition) {
            onLoadMore(recyclerView);
        }
    }

    private boolean canTriggerLoadMore(RecyclerView recyclerView) {
        View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        int position = recyclerView.getChildLayoutPosition(lastChild);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        assert layoutManager != null;
        int totalItemCount = layoutManager.getItemCount();
        return totalItemCount - 1 == position;
    }

    public abstract void onLoadMore(RecyclerView recyclerView);
}
