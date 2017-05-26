package com.androidannotations.net;

import com.androidannotations.net.spring.JsonToModelConverter;
import com.example.zyfx_.myapplication.bean.BaseEntity;
import com.example.zyfx_.myapplication.bean.UserInfo;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresCookie;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.annotations.rest.SetsCookie;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zyfx_ on 2017/5/15.
 */
@Rest(rootUrl = "http://wallet.pigamegroup.com/", converters = { JsonToModelConverter.class, FormHttpMessageConverter.class})
public interface RestClient {

    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);

    void setRootUrl(String rootUrl);

    void setHeader(String name, String value);

    String getHeader(String name);

    void setCookie(String name, String value);

    String getCookie(String name);


    @Post("user/applogin?username={username}&password={password}&clientVersion{clientVersion}" +
            "&androidDeviceToken{androidDeviceToken}&noticePlatform{noticePlatform}&clientType{clientType}")
    @Accept(MediaType.APPLICATION_JSON)
    UserInfo signInApp(String username, String password, String clientVersion, String androidDeviceToken, int noticePlatform, int clientType);

    @Post("user/applogin?username={username}&token={token}")
    @Accept(MediaType.APPLICATION_JSON)
    UserInfo tokenLogin(String username, String token);

    @Get("user/logout")
    @RequiresHeader("cookie")
    @Accept(MediaType.APPLICATION_JSON)
    BaseEntity loginOut();
}
