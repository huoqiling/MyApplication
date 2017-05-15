package com.androidannotations.net;

import com.androidannotations.base.BaseRestManager;
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

    @AfterInject
    protected void initAuth() {

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(testInterceptor);
        SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
        s.setConnectTimeout(CONNECT_TIME_OUT);
        s.setReadTimeout(READ_TIME_OUT);
        {
            RestTemplate template = restClient.getRestTemplate();
            template.setRequestFactory(s);
            template.setInterceptors(interceptors);
        }
    }

    @Override
    protected void notifyResult(OnRestListener onRestListener, BaseEntity baseInfo) {
        log(restClient.getHeader("JSESSIONID"));
        super.notifyResult(onRestListener, baseInfo);
    }

    @Background
    public void login(OnRestListener onRestListener, String userName, String password) {

        try {
            prepareRequest(onRestListener);
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("username", userName);
            formData.add("password", password);
            UserInfo userResult = restClient.signInApp(userName, password);
            notifyResult(onRestListener, userResult);
        } catch (Exception e) {
            e.printStackTrace();
            notifyRequestFail(onRestListener);
        } finally {
            notifyFinally(onRestListener);
        }
    }

    public void setSesstionId(String sessionId) {
        restClient.setCookie("JSESSIONID", sessionId);
    }
}
