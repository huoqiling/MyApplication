package com.androidannotations.cache;

import android.text.TextUtils;
import android.widget.TextView;

import com.androidannotations.view.actvity.SignInActivity;
import com.example.zyfx_.myapplication.CustomApplication;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

/**
 * Created by zyfx_ on 2017/5/25.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CookieCache {

    @App
    CustomApplication application;

    private SecurityStorage storage;

    @AfterInject
    void init() {
        storage = new SecurityStorage(application, PreferencesKey.Cache.COOKIE);
    }

    public void saveCookie(String cookie) {
        if (!TextUtils.isEmpty(cookie)) {
            storage.put(PreferencesKey.Cache.COOKIE, cookie);
        }
    }

    public String getCookie() {
        return storage.getString(PreferencesKey.Cache.COOKIE, "");
    }
}
