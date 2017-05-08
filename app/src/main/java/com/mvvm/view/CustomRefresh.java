package com.mvvm.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.zyfx_.myapplication.R;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * Created by zyfx_ on 2017/5/6.
 */
public class CustomRefresh extends TwinklingRefreshLayout {


    public CustomRefresh(Context context) {
        this(context, null);
    }

    public CustomRefresh(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        ProgressLayout progressLayout = new ProgressLayout(context);
        progressLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        progressLayout.setColorSchemeResources(R.color.white);
        setHeaderView(progressLayout);
    }
}
