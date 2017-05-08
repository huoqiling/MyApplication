package com.example.zyfx_.myapplication.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.zyfx_.myapplication.R;

import butterknife.ButterKnife;

/**
 * @Author zhangxin
 * @date 2017/3/17 10:27
 * @description 父类activity
 **/
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
        setListener();
    }

    public abstract int getLayoutId();

    public abstract void init();

    public abstract void setListener();

    public abstract void onBackListener();

    public void setBackToolbar(Toolbar mToolbar, String title) {
        mToolbar.setNavigationIcon(R.drawable.back_white);
        mToolbar.setTitleTextColor(getResourceColorId(R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //显示小箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackListener();
            }
        });
        setTitle(title);
    }

    public int getResourceColorId(int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(color, null);
        } else {
            return getResources().getColor(color);
        }
    }

    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
