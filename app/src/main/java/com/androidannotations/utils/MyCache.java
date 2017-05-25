package com.androidannotations.utils;

import android.text.TextUtils;

import com.androidannotations.cache.PreferencesKey;
import com.androidannotations.cache.SecurityStorage;
import com.example.zyfx_.myapplication.CustomApplication;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

/**
 * Created by zyfx_ on 2017/5/25.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MyCache {

    @App
    CustomApplication application;

    private SecurityStorage storage;

    @AfterInject
    void init() {
        storage = new SecurityStorage(application, PreferencesKey.Cache.NAME);
    }


    public void saveUserName(String userName) {
        if (!TextUtils.isEmpty(userName)) {
            storage.put(PreferencesKey.Cache.NAME, userName);
        }
    }

    public String getUserName() {
        return storage.getString(PreferencesKey.Cache.NAME, "hello");
    }

    public void setLoginState(boolean isLogin) {
        storage.put(PreferencesKey.Cache.HAS_LOGIN, isLogin);
    }

    public boolean getLoginState() {
        return storage.getBoolean(PreferencesKey.Cache.HAS_LOGIN, false);
    }

    public void saveToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            storage.put(PreferencesKey.Cache.TOKEN, token);
        }
    }

    public String getToken() {
        return storage.getString(PreferencesKey.Cache.TOKEN, "");
    }
}
