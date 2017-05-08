package com.example.zyfx_.myapplication.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.adapter.PhotoAdapter;
import com.example.zyfx_.myapplication.base.BaseFragment;
import com.example.zyfx_.myapplication.bean.FoodBean;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author zhangxin
 * @date 2017/3/17 17:48
 * @description 首页
 **/
public class HomeFragment extends BaseFragment {

    @Bind(R.id.backdrop)
    ImageView backdrop;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private PhotoAdapter photoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        collapsingToolbar.setTitle("猫的仰望");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        photoAdapter = new PhotoAdapter();
        recyclerView.setAdapter(photoAdapter);
        List<FoodBean> foodBeanList = new ArrayList<>();
        foodBeanList.add(new FoodBean(R.drawable.photo1, "photo1"));
        foodBeanList.add(new FoodBean(R.drawable.photo2, "photo2"));
        foodBeanList.add(new FoodBean(R.drawable.photo3, "photo3"));
        foodBeanList.add(new FoodBean(R.drawable.photo4, "photo4"));
        foodBeanList.add(new FoodBean(R.drawable.photo5, "photo5"));
        foodBeanList.add(new FoodBean(R.drawable.photo6, "photo6"));
        foodBeanList.add(new FoodBean(R.drawable.photo7, "photo7"));
        photoAdapter.setDataList(foodBeanList);
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
