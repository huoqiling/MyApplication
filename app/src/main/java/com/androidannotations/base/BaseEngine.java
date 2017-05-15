package com.androidannotations.base;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidannotations.CustomLoading;
import com.androidannotations.ToastUtil;
import com.example.zyfx_.myapplication.CustomApplication;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

/**
 * Created by zyfx_ on 2017/5/12.
 */
@EBean
public class BaseEngine {

    @App
    protected CustomApplication application;

    @Bean
    protected ToastUtil toastUtil;

    private CustomLoading customLoading;
    private FragmentActivity activity;


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @UiThread
    public void showProgress(boolean cancellable) {
        try {
            if (null != activity) {
                customLoading = new CustomLoading();
                customLoading.setIsCancellable(cancellable)
                        .show(activity.getSupportFragmentManager(), "loadingDialog");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void dismissProgress() {
        try {
            if (null != customLoading) {
                customLoading.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void log(String msg) {
        if (msg != null) {
            Log.v("zhangx", msg);
        }
    }
}
