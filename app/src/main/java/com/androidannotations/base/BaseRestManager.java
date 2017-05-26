package com.androidannotations.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.annotation.UiThread;
import android.util.Log;

import com.androidannotations.net.MyClientHttpRequestInterceptor;
import com.example.zyfx_.myapplication.bean.BaseEntity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by zyfx_ on 2017/5/15.
 */
@EBean
abstract public class BaseRestManager extends BaseEngine {

    @SystemService
    protected WifiManager wifiManager;

    @Bean
    protected MyClientHttpRequestInterceptor interceptor;

    @UiThread
    protected void prepareRequest(OnRestListener onRestListener) {
        if (onRestListener.isShowProgressDialog()) {
            showProgress(onRestListener.isCancelable());
        }
    }

    public interface OnRestListener {

        void onRequestSuccess(BaseEntity baseInfo);

        void onRequestFail();

        boolean isShowProgressDialog();

        boolean isCancelable();
    }


    @UiThread
    protected void notifyResult(OnRestListener onRestListener, BaseEntity baseInfo) {
        dismissProgress();
        if (null != baseInfo) {
            onRestListener.onRequestSuccess(baseInfo);
            dumpObject(baseInfo);
        } else {
            toastUtil.showTextToast("数据获取失败！");
        }
    }

    @UiThread
    protected void handleRequestException(Exception e, OnRestListener onRestListener) {
        dismissProgress();
        e.printStackTrace();
        if (e.getClass().isInstance(HttpClientErrorException.class)) {
            toastUtil.showTextToast("无法连接");
        } else {
            if (checkNetworkState()) {
                toastUtil.showTextToast("网络差，请重试..");
            } else {
                toastUtil.showTextToast("网络不可用,请打开wifi或数据网络");
            }
        }
    }

    @UiThread
    public void dumpObject(Object object) {
        Log.v("zhangx-RestManager", "dumpObject:");
    }

    @UiThread
    protected void notifyRequestFail(OnRestListener onRestListener) {
        dismissProgress();
        onRestListener.onRequestFail();
    }

    @UiThread
    protected void notifyFinally(OnRestListener onRestListener) {

    }


    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean checkNetworkState() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }


    private boolean getWifiState() {
        log("getWifiState");
        int wifiState = wifiManager.getWifiState();
        log("" + wifiState);
        return wifiState == WifiManager.WIFI_STATE_ENABLED;
    }

    public boolean checkConnectivity(OnRestListener onRestListener) {
        log("checkConnectivity");
        if (!getWifiState()) {
            notifyRequestFail(onRestListener);
            toastUtil.showTextToast("网络不可用,请打开wifi或数据网络");
            return false;
        }
        return true;
    }

}
