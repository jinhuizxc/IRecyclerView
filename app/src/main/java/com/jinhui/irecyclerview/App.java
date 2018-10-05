package com.jinhui.irecyclerview;

import android.app.Application;

import com.jinhui.irecyclerview.network.OkHttp;

/**
 * Created by jh on 2018/10/4.
 * Email: 1004260403@qq.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化
        OkHttp.init(getApplicationContext());
    }
}
