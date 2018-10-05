package com.jinhui.library;

/**
 * Created by jh on 2018/10/5.
 * Email: 1004260403@qq.com
 */
public interface RefreshTrigger {

    void onStart(boolean automatic, int headerHeight, int finalHeight);

    void onMove(boolean finished, boolean automatic, int moved);

    void onRefresh();

    void onRelease();

    void onComplete();

    void onReset();

}
