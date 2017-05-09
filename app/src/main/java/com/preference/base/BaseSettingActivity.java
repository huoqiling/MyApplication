package com.preference.base;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.zyfx_.myapplication.R;

/**
 * Created by zyfx_ on 2017/5/9.
 */
public abstract class BaseSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_setting);
        getFragmentManager().beginTransaction().replace(R.id.container, getSettingFragment()).commitAllowingStateLoss();
    }

    public abstract PreferenceFragment getSettingFragment();
}
