package com.androidannotations;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.androidannotations.view.BottomTabView;
import com.example.zyfx_.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by zyfx_ on 2017/5/16.
 */
@EActivity(R.layout.activity_annotations_main)
public abstract class BottomTabBaseActivity extends AppCompatActivity {

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

    protected abstract List<BottomTabView.TabItemView> getTabViews();

    protected abstract List<Fragment> getFragments();

    protected View getCenterView() {
        return null;
    }

}
