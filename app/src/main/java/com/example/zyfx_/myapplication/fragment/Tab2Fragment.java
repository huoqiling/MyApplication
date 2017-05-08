package com.example.zyfx_.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * @Author zhangxin
 * @date 2017/3/17 17:48
 * @description 首页
 **/
public class Tab2Fragment extends BaseFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_tab2;
    }

    @Override
    protected void initView(View view) {

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
