package com.example.zyfx_.myapplication.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.base.BaseActivity;
import com.example.zyfx_.myapplication.fragment.VideoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoActivity extends BaseActivity {


    @Bind(R.id.container)
    FrameLayout container;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void init() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onBackListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new VideoFragment()).commit();
        }
    }
}
