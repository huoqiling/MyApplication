package com.example.zyfx_.myapplication.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author: star
 * @time: 2016/7/11 15:57
 * @description: 基类
 */
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public String msg;
    private String resultCode;
    public String status;
    public T Data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (null != status && !TextUtils.isEmpty(status)) {
            return status.equalsIgnoreCase("success");
        }
        return false;
    }

    /**
     * 错误
     *
     * @return
     */
    public boolean isFailed() {
        if (null != status && !TextUtils.isEmpty(status)) {
            return status.equalsIgnoreCase("failed");
        }
        return false;
    }

}
