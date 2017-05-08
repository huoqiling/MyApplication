package com.example.zyfx_.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.adapter.FoodAdapter;
import com.example.zyfx_.myapplication.base.BaseActivity;
import com.example.zyfx_.myapplication.bean.FoodBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author zhangxin
 * @date 2017/3/17 15:22
 * @description
 **/
public class FoodActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;

    private FoodAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        setBackToolbar(toolbar, "首页");
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FoodAdapter();
        recyclerView.setAdapter(mAdapter);
        ProgressLayout headerView = new ProgressLayout(this);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
                refreshData();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);

                loadMoreData();
            }


        });
        refreshData();
    }

    private void refreshData() {

        List<FoodBean> foodBeanList = new ArrayList<>();
        foodBeanList.add(new FoodBean(R.drawable.food1, "三文鱼"));
        foodBeanList.add(new FoodBean(R.drawable.food2, "青菜披萨"));
        foodBeanList.add(new FoodBean(R.drawable.food3, "汉堡包"));
        foodBeanList.add(new FoodBean(R.drawable.food4, "草莓桑葚"));
        foodBeanList.add(new FoodBean(R.drawable.food5, "青椒披萨"));
        mAdapter.setDataList(foodBeanList);
    }

    private void loadMoreData() {
        List<FoodBean> foodBeanList = new ArrayList<>();
        foodBeanList.add(new FoodBean(R.drawable.food1, "三文鱼"));
        foodBeanList.add(new FoodBean(R.drawable.food2, "青菜披萨"));
        foodBeanList.add(new FoodBean(R.drawable.food3, "汉堡包"));
        mAdapter.addItems(foodBeanList);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onBackListener() {
        finish();
    }
}
