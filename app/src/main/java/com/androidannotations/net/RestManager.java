package com.androidannotations.net;

import android.text.TextUtils;
import android.util.Log;

import com.androidannotations.base.BaseRestManager;
import com.androidannotations.entitys.InquiryRecordInfo;
import com.example.zyfx_.myapplication.bean.BaseEntity;
import com.example.zyfx_.myapplication.bean.UserInfo;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyfx_ on 2017/5/12.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RestManager extends BaseRestManager {

    private static final int CONNECT_TIME_OUT = 15 * 1000;

    private static final int READ_TIME_OUT = 15 * 1000;

    @RestService
    RestClient restClient;

    @RestService
    RestBossClient bossClient;


    @AfterInject
    protected void initAuth() {

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(interceptor);
        SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
        s.setConnectTimeout(CONNECT_TIME_OUT);
        s.setReadTimeout(READ_TIME_OUT);
        {
            RestTemplate template = restClient.getRestTemplate();
            template.setRequestFactory(s);
            template.setInterceptors(interceptors);
        }

        {
            RestTemplate template = bossClient.getRestTemplate();
            template.setRequestFactory(s);
            template.setInterceptors(interceptors);
        }
    }

    @Override
    protected void notifyResult(OnRestListener onRestListener, BaseEntity baseInfo) {
        super.notifyResult(onRestListener, baseInfo);
    }

    @Background
    public void login(OnRestListener onRestListener, String userName, String password) {

        try {
            prepareRequest(onRestListener);
            UserInfo userInfo = restClient.signInApp(userName, password, "1.0", getDeviceId(), 1, 1);
            notifyResult(onRestListener, userInfo);
        } catch (Exception e) {
            handleRequestException(e, onRestListener);
            notifyRequestFail(onRestListener);
        } finally {
            notifyFinally(onRestListener);
        }
    }

    @Background
    public void tokenLogin(OnRestListener onRestListener, String userName, String token) {
        try {
            prepareRequest(onRestListener);
            UserInfo userInfo = restClient.tokenLogin(userName, token);
            notifyResult(onRestListener, userInfo);
        } catch (Exception e) {
            handleRequestException(e, onRestListener);
            notifyRequestFail(onRestListener);
        } finally {
            notifyFinally(onRestListener);
        }
    }

    @Background
    public void getInquiryList(OnRestListener onRestListener, int pageIndex, int pageSize, int status) {
        try {
            prepareRequest(onRestListener);
            InquiryRecordInfo recordInfo = bossClient.getInquiryInfo(pageIndex, pageSize, status);
            notifyResult(onRestListener, recordInfo);
        } catch (Exception e) {
            handleRequestException(e, onRestListener);
            notifyRequestFail(onRestListener);
        } finally {
            notifyFinally(onRestListener);
        }
    }

    @Background
    public void loginOut(OnRestListener onRestListener) {
        try {
            prepareRequest(onRestListener);
            BaseEntity baseEntity = restClient.loginOut();
            notifyResult(onRestListener, baseEntity);
        } catch (Exception e) {
            handleRequestException(e, onRestListener);
            notifyRequestFail(onRestListener);
        } finally {
            notifyFinally(onRestListener);
        }
    }

    public void setCookie(String cookie) {
        if (!TextUtils.isEmpty(cookie)) {
            Log.i("zhangx-restManage", "cookie--" + cookie);
            restClient.setHeader("cookie", cookie);
            bossClient.setHeader("cookie", cookie);
        }
    }
}
