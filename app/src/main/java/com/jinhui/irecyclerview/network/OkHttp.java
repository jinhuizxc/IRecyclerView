package com.jinhui.irecyclerview.network;

import android.content.Context;

import com.jinhui.irecyclerview.util.FileUtils;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by jh on 2018/10/4.
 * Email: 1004260403@qq.com
 */

public class OkHttp {

    private static final int MAX_CACHE_SIZE = 200 * 1024 * 1024;
    private static OkHttpClient sOkHttpClient;

    public static void init(Context context) {
        Context applicationContext = context.getApplicationContext();

        sOkHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(FileUtils.getHttpCacheDir(applicationContext), MAX_CACHE_SIZE))
                .build();
    }

    public static OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }

}
