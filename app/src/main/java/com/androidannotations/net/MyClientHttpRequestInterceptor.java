package com.androidannotations.net;

import android.util.Log;

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

    @Bean
    CookieCacheHelper cacheHelper;

    @App
    CustomApplication application;

    private static final String SET_COOKIE = "set-cookie";
    private static final String COOKIE = "cookie";
    private static final String COOKIE_STORE = "cookieStore";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] byteArray, ClientHttpRequestExecution execution) throws IOException {
        Log.d(getClass().getSimpleName(), ">>> entering intercept");
        List<String> cookies = request.getHeaders().get(COOKIE);
        // if the header doesn't exist, add any existing, saved cookies
        if (cookies != null) {
            Log.d(getClass().getSimpleName(), cookies.toString());
        } else {
            List<String> cookieStore = (List<String>) cacheHelper.retrieveObjectFromCache(COOKIE_STORE);
            // if we have stored cookies, add them to the headers
            if (cookieStore != null) {
                for (String cookie : cookieStore) {
                    request.getHeaders().add(COOKIE, cookie);
                }
            }
            Log.d(getClass().getSimpleName(), ">>> add intercept");

        }
        // execute the request
        ClientHttpResponse response = execution.execute(request, byteArray);
        // pull any cookies off and store them
        cookies = response.getHeaders().get(SET_COOKIE);
        if (cookies != null) {
            for (String cookie : cookies) {
                Log.d(getClass().getSimpleName(), ">>> response cookie = " + cookie);
            }
            cacheHelper.storeObjectInCache(COOKIE_STORE, cookies);
        }
        Log.d(getClass().getSimpleName(), ">>> leaving intercept");
        return response;
    }
}
