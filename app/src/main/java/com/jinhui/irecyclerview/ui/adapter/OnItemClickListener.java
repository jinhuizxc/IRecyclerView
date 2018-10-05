package com.jinhui.irecyclerview.ui.adapter;

import android.view.View;

/**
 * Created by jh on 2018/10/5.
 * Email: 1004260403@qq.com
 */
public interface OnItemClickListener<T> {

    void onItemClick(int position, T t, View v);

}
