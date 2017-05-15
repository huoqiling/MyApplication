package com.androidannotations;

import com.example.zyfx_.myapplication.bean.UserInfo;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;

/**
 * Created by zyfx_ on 2017/5/12.
 */
@Rest(rootUrl = "http://wallet.pigamegroup.com", converters = {GsonHttpMessageConverter.class})
public interface SignInService extends RestClientErrorHandling {

    @Post("/user/applogin")
    @Accept(MediaType.APPLICATION_JSON)
    ResponseEntity<UserInfo> signInApp(LinkedMultiValueMap<String, String> formData);


}
