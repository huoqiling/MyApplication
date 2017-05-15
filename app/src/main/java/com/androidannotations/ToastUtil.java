package com.androidannotations;

import android.widget.Toast;

import com.example.zyfx_.myapplication.CustomApplication;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

/**
 *
 *@author: star
 *@time: 2016/7/9 17:55
 *@description: 自定义toast
 *
 */
@EBean
public class ToastUtil {

    private static Toast mToast = null;

    /**
     * 非延时toast
     *
     * @param
     * @param msg
     */
    @UiThread
    public void showTextToast(String msg) {
        if (msg == null || "".equals(msg.trim()) || msg.equalsIgnoreCase("null")) {
            return;
        }
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(CustomApplication.getInstance(), msg + "", Toast.LENGTH_LONG);
        mToast.show();
    }



}
