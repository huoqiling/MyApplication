package com.androidannotations;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.androidannotations.view.BottomTabView;
import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.fragment.FoodFragment;
import com.example.zyfx_.myapplication.fragment.FriendFragment;
import com.example.zyfx_.myapplication.fragment.HomeFragment;
import com.example.zyfx_.myapplication.fragment.MineFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyfx_ on 2017/5/16.
 */
@EActivity(R.layout.activity_base_bottom_tab)
public class AnnotationsMainActivity extends AppCompatActivity {


    @ViewById
    ViewPager viewPager;

    @ViewById
    BottomTabView bottomTabView;

    private FragmentPagerAdapter adapter;

    @AfterViews
    void init() {
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragments().get(position);
            }

            @Override
            public int getCount() {
                return getFragments().size();
            }
        };
        viewPager.setAdapter(adapter);

        bottomTabView.setTabItemViews(getTabViews());
    }

    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViewList = new ArrayList<>();
        tabItemViewList.add(new BottomTabView.TabItemView(this, "首页", R.color.colorPrimary, R.color.colorAccent,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViewList.add(new BottomTabView.TabItemView(this, "美食", R.color.colorPrimary, R.color.colorAccent,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViewList.add(new BottomTabView.TabItemView(this, "朋友", R.color.colorPrimary, R.color.colorAccent,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        tabItemViewList.add(new BottomTabView.TabItemView(this, "我的", R.color.colorPrimary, R.color.colorAccent,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher_round));
        return tabItemViewList;
    }

    protected List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new FoodFragment());
        fragmentList.add(new FriendFragment());
        fragmentList.add(new MineFragment());
        return fragmentList;
    }
}
