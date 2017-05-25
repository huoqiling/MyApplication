package com.androidannotations.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.androidannotations.base.BaseAnnotationsFragment;
import com.androidannotations.view.view.BottomTabView;
import com.example.zyfx_.myapplication.CustomApplication;
import com.example.zyfx_.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyfx_ on 2017/5/18.
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends BaseAnnotationsFragment {

    @ViewById
    ViewPager viewPager;

    @ViewById
    BottomTabView bottomTabView;

    @App
    CustomApplication application;

    private IndexFragment indexFragment;
    private FavouriteFragment favouriteFragment;
    private MessageFragment messageFragment;

    private FragmentPagerAdapter pagerAdapter;

    @AfterViews
    void init() {
        initFragment();
        bottomTabView.setTabItemViews(getTabViews());
        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getListFragments().get(position);
            }

            @Override
            public int getCount() {
                return getListFragments().size();
            }
        };
        viewPager.setAdapter(pagerAdapter);
        bottomTabView.setOnTabItemSelectListener(new BottomTabView.OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                viewPager.setCurrentItem(position, false);
            }
        });

    }

    private void initFragment() {
        indexFragment = new IndexFragment_();
        favouriteFragment = new FavouriteFragment_();
        messageFragment = new MessageFragment_();
    }

    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViewList = new ArrayList<>();
        tabItemViewList.add(new BottomTabView.TabItemView(application, "首页", R.color.colorPrimary, R.color.colorAccent,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViewList.add(new BottomTabView.TabItemView(application, "喜好", R.color.colorPrimary, R.color.colorAccent,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViewList.add(new BottomTabView.TabItemView(application, "个人中心", R.color.colorPrimary, R.color.colorAccent,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        return tabItemViewList;
    }

    private List<Fragment> getListFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(indexFragment);
        fragmentList.add(favouriteFragment);
        fragmentList.add(messageFragment);
        return fragmentList;
    }
}
