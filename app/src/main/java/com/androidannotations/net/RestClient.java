package com.androidannotations.net;

import com.example.zyfx_.myapplication.bean.UserInfo;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zyfx_ on 2017/5/15.
 */
@Rest(rootUrl = "http://web.goldcolordevworld2017.com", converters = {GsonHttpMessageConverter.class})
public interface RestClient {

    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);

    void setRootUrl(String rootUrl);

    void setHeader(String name, String value);

    String getHeader(String name);

    void setCookie(String name, String value);

    String getCookie(String name);


    @Post("/user/applogin?username={username}&password={password}")
    @Accept(MediaType.APPLICATION_JSON)
    UserInfo signInApp(String username, String password);

}
