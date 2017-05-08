package com.example.zyfx_.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.adapter.FoodAdapter;
import com.example.zyfx_.myapplication.base.BaseFragment;
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
 * @date 2017/3/17 17:48
 * @description 食物
 **/
public class FoodFragment extends BaseFragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;

    private FoodAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_food;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FoodAdapter();
        recyclerView.setAdapter(mAdapter);
        ProgressLayout headerView = new ProgressLayout(getActivity());
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
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
