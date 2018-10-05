package com.jinhui.irecyclerview.okhttp3;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinhui.irecyclerview.network.NetworkApi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by jh on 2018/10/5.
 * Email: 1004260403@qq.com
 */
public class GsonCallBackWrapper<T> implements okhttp3.Callback {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    private static final Gson sGson = new Gson();

    private NetworkApi.Callback<T> mCallback;

    private TypeToken<T> mTypeToken;

    /**
     * @param callback  callback to be wrapped
     * @param typeToken why we need a TypeToken instance? see: https://github.com/google/gson/issues/828
     */
    public GsonCallBackWrapper(NetworkApi.Callback<T> callback, TypeToken<T> typeToken) {
        this.mCallback = callback;
        this.mTypeToken = typeToken;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            final T t = sGson.getAdapter(mTypeToken).fromJson(responseBody.charStream());
            deliverToMainThread(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSuccess(t);
                }
            });
        }
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        deliverToMainThread(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(e);
            }
        });
    }

    public void deliverToMainThread(Runnable runnable) {
        sHandler.post(runnable);
    }

}
