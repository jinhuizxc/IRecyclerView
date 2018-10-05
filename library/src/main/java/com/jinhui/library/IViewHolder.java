package com.jinhui.library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jh on 2018/10/5.
 * Email: 1004260403@qq.com
 */
public class IViewHolder extends RecyclerView.ViewHolder {

    public IViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Deprecated
    public final int getIPosition() {
        return getPosition() - 2;
    }

    public final int getILayoutPosition() {
        return getLayoutPosition() - 2;
    }

    public final int getIAdapterPosition() {
        if (getAdapterPosition() == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }
        return getAdapterPosition() - 2;
    }

    public final int getIOldPosition() {
        return getOldPosition() - 2;
    }

    public final long getIItemId() {
        return getItemId();
    }

    public final int getIItemViewType() {
        return getItemViewType();
    }
}
