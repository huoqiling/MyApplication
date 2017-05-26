package com.androidannotations.net;

import com.androidannotations.entitys.InquiryRecordInfo;
import com.androidannotations.net.spring.JsonToModelConverter;

import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zyfx_ on 2017/5/15.
 */
@Rest(rootUrl = "http://boss.pigamegroup.com/", converters = {JsonToModelConverter.class, FormHttpMessageConverter.class})
public interface RestBossClient {

    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);

    void setRootUrl(String rootUrl);

    void setHeader(String name, String value);

    String getHeader(String name);

    void setCookie(String name, String value);

    String getCookie(String name);

    @Post("worldmap/queryWorldMapAskListOfAskUser?pageIndex={pageIndex}&pageSize={pageSize}&status={status}")
    @RequiresHeader("cookie")
    InquiryRecordInfo getInquiryInfo(int pageIndex, int pageSize, int status);

}
