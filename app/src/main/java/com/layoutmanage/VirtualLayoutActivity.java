package com.layoutmanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.zyfx_.myapplication.R;

/**
 * Created by zyfx_ on 2017/5/10.
 */
public class VirtualLayoutActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_layout);
    }
}
