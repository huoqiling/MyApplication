package com.androidannotations.net;

import android.util.Log;

import com.androidannotations.cache.CookieCache;
import com.example.zyfx_.myapplication.CustomApplication;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * @Author zhangxin
 * @date 2017/5/24 15:23
 * @description 我的拦截器
 **/
@EBean(scope = EBean.Scope.Singleton)
public class MyClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @App
    CustomApplication application;

    @Bean
    CookieCache cookieCache;

    private static final String SET_COOKIE = "set-cookie";
    private static final String COOKIE = "cookie";
    private static final String COOKIE_STORE = "cookieStore";


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] byteArray, ClientHttpRequestExecution execution) throws IOException {
        Log.d(getClass().getSimpleName(), ">>> entering intercept");
        ClientHttpResponse response = execution.execute(request, byteArray);
        List<String> cookies = response.getHeaders().get(SET_COOKIE);
        if (cookies != null) {
            for (String cookie : cookies) {
                Log.d(getClass().getSimpleName(), ">>> response cookie = " + cookie);
                cookieCache.saveCookie(cookie);
            }
        }
        Log.d(getClass().getSimpleName(), ">>> leaving intercept");
        return response;
    }
}
