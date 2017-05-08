package com.example.zyfx_.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author zhangxin
 * @date 2017/3/17 15:42
 * @description 首页viewpager适配器
 **/
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] tabTitles = new String[]{"首页", "食物", "朋友", "我的"};

    private int[] imageResId = new int[]{R.drawable.selector_tab_home, R.drawable.selector_tab_exchange,
            R.drawable.selector_tab_friend, R.drawable.selector_tab_mine};

    private List<BaseFragment> fragments = new ArrayList<>();

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context, List<BaseFragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab, null);
        ViewHolder holder = new ViewHolder(view);
        holder.imageView.setImageResource(imageResId[position]);
        holder.textView.setText(tabTitles[position]);
        return view;
    }

    static class ViewHolder {

        @Bind(R.id.imageView)
        ImageView imageView;

        @Bind(R.id.textView)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
