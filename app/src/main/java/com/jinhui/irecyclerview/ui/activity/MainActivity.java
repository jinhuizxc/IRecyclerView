package com.jinhui.irecyclerview.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jinhui.irecyclerview.R;
import com.jinhui.irecyclerview.model.Image;
import com.jinhui.irecyclerview.network.NetworkApi;
import com.jinhui.irecyclerview.ui.adapter.ImageAdapter;
import com.jinhui.irecyclerview.ui.adapter.OnItemClickListener;
import com.jinhui.irecyclerview.util.DensityUtils;
import com.jinhui.irecyclerview.util.ListUtils;
import com.jinhui.irecyclerview.widget.BannerView;
import com.jinhui.irecyclerview.widget.footer.LoadMoreFooterView;
import com.jinhui.irecyclerview.widget.header.BatVsSupperHeaderView;
import com.jinhui.irecyclerview.widget.header.ClassicRefreshHeaderView;
import com.jinhui.library.IRecyclerView;
import com.jinhui.library.listener.OnLoadMoreListener;
import com.jinhui.library.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * https://github.com/Aspsine/IRecyclerView
 */
public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener, OnItemClickListener<Image> {

    @BindView(R.id.iRecyclerView)
    IRecyclerView iRecyclerView;

    private BannerView bannerView;
    private LoadMoreFooterView loadMoreFooterView;

    private ImageAdapter mAdapter;

    private int mPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        iRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bannerView = (BannerView) LayoutInflater.from(this).inflate(R.layout.layout_banner_view, iRecyclerView.getHeaderContainer(), false);

        loadMoreFooterView = (LoadMoreFooterView) iRecyclerView.getLoadMoreFooterView();

        mAdapter = new ImageAdapter();
        iRecyclerView.setIAdapter(mAdapter);

        iRecyclerView.setOnRefreshListener(this);
        iRecyclerView.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(this);

        /**
         * 设置刷新
         */
        iRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(true);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_header, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_header) {
            toggleRefreshHeader();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 切换刷新的头部view
     */
    private void toggleRefreshHeader() {
        if (iRecyclerView.getRefreshHeaderView() instanceof BatVsSupperHeaderView) {
            ClassicRefreshHeaderView classicRefreshHeaderView = new ClassicRefreshHeaderView(this);
            classicRefreshHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(this, 80)));
            // we can set view
            iRecyclerView.setRefreshHeaderView(classicRefreshHeaderView);
            Toast.makeText(this, "Classic style", Toast.LENGTH_SHORT).show();
        } else if (iRecyclerView.getRefreshHeaderView() instanceof ClassicRefreshHeaderView) {
            // we can also set layout
            iRecyclerView.setRefreshHeaderView(R.layout.layout_irecyclerview_refresh_header);
            Toast.makeText(this, "Bat man vs Super man style", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        loadBanner();
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        refresh();
    }

    @Override
    public void onLoadMore() {
        if (loadMoreFooterView.canLoadMore() && mAdapter.getItemCount() > 0) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
            loadMore();
        }
    }

    private void loadMore() {
        NetworkApi.requestImages(mPage, new NetworkApi.Callback<List<Image>>() {
            @Override
            public void onSuccess(final List<Image> images) {
                if (ListUtils.isEmpty(images)) {
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                } else {
                    mPage++;
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                    mAdapter.append(images);
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载banner
     */
    private void loadBanner() {
        NetworkApi.requestBanners(new NetworkApi.Callback<List<Image>>() {
            @Override
            public void onSuccess(List<Image> images) {
                if (!ListUtils.isEmpty(images)) {
                    bannerView.setList(images);
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 请求数据
     */
    private void refresh() {
        mPage = 1;
        NetworkApi.requestImages(mPage, new NetworkApi.Callback<List<Image>>() {
            @Override
            public void onSuccess(List<Image> images) {
                iRecyclerView.setRefreshing(false);
                if (ListUtils.isEmpty(images)) {
                    mAdapter.clear();
                } else {
                    mPage = 2;
                    mAdapter.setList(images);
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                iRecyclerView.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onItemClick(int position, Image image, View v) {
        // 移除当前项
//        mAdapter.remove(position);
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
