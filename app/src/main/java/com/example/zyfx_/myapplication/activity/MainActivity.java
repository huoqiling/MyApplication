package com.example.zyfx_.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.adapter.SimpleFragmentPagerAdapter;
import com.example.zyfx_.myapplication.base.BaseActivity;
import com.example.zyfx_.myapplication.base.BaseFragment;
import com.example.zyfx_.myapplication.fragment.FoodFragment;
import com.example.zyfx_.myapplication.fragment.FriendFragment;
import com.example.zyfx_.myapplication.fragment.HomeFragment;
import com.example.zyfx_.myapplication.fragment.MineFragment;
import com.example.zyfx_.myapplication.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.viewPager)
    NoScrollViewPager viewPager;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.login)
    TextView login;

    private SimpleFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(getResourceColorId(R.color.white));
        setSupportActionBar(mToolbar);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FoodFragment());
        fragments.add(new FriendFragment());
        fragments.add(new MineFragment());

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
                ImageView imageView = (ImageView) tab.getCustomView().findViewById(R.id.imageView);
                imageView.setFocusable(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView) tab.getCustomView().findViewById(R.id.imageView);
                imageView.setFocusable(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void setListener() {

    }

    @Override
    public void onBackListener() {

    }


    @OnClick({R.id.video, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video:
                Intent intentVideo = new Intent(this, VideoActivity.class);
                startActivity(intentVideo);
                break;
            case R.id.login:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
        }
    }
}
