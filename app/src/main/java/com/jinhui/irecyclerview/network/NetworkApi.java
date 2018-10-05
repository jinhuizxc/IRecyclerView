package com.jinhui.irecyclerview.network;

import com.google.gson.reflect.TypeToken;
import com.jinhui.irecyclerview.model.Constants;
import com.jinhui.irecyclerview.model.Image;
import com.jinhui.irecyclerview.okhttp3.GsonCallBackWrapper;

import java.util.List;

import okhttp3.Request;

/**
 * Created by jh on 2018/10/5.
 * Email: 1004260403@qq.com
 */
public class NetworkApi {

    public static void requestImages(int page, final Callback<List<Image>> callback) {
        String url = Constants.ImagesAPI(page);
        final Request request = new Request.Builder().get().url(url).build();
        OkHttp.getOkHttpClient().newCall(request).enqueue(new GsonCallBackWrapper<>(callback, new TypeToken<List<Image>>() {
        }));
    }

    public static void requestBanners(final Callback<List<Image>> callback) {
        String url = Constants.BannerAPI;
        final Request request = new Request.Builder().get().url(url).build();
        OkHttp.getOkHttpClient().newCall(request).enqueue(new GsonCallBackWrapper<>(callback, new TypeToken<List<Image>>() {
        }));
    }

    public interface Callback<T> {
        void onSuccess(T t);

        void onFailure(Exception e);
    }

}
