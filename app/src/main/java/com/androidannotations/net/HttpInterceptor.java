package com.androidannotations.net;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@EBean(scope = Scope.Singleton)
public class HttpInterceptor implements ClientHttpRequestInterceptor {

    public static final String TAG = "zhangx-HttpInterceptor";

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        testLog(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        testCheckResponse(response);
        return response;
    }

    private String stringFromBytes(byte[] _bytes) {
        String file_string = "";
        for (int i = 0; i < _bytes.length; i++) {
            file_string += (char) _bytes[i];
        }
        return file_string;
    }

    private void testLog(HttpRequest request, byte[] body) {
        log("testLog");
        String url = request.getURI().toString();
        Log.v(TAG, "url:");
        Log.v(TAG, url);
        Log.v(TAG, url);
        Log.v(TAG, "body");
        Log.v(TAG, stringFromBytes(body));
        HttpHeaders headers = request.getHeaders();
        String cookie = headers.getFirst(HttpHeaders.COOKIE);
        log(cookie);
        String sessionid = headers.getFirst("sessionid");
        log(sessionid);
    }

    private void log(String log) {
        if (log != null) {
            Log.v(TAG, log);
        }
    }

    private void testCheckResponse(ClientHttpResponse response) {
        log("testCheckResponse");
        HttpHeaders headers = response.getHeaders();
        List<MediaType> accepts = headers.getAccept();
        String cacheControl = headers.getCacheControl();
        List<String> connection = headers.getConnection();
        List<Charset> acceptCharset = headers.getAcceptCharset();
        List<ContentCodingType> acceptEncoding = headers.getAcceptEncoding();
        String acceptLanguage = headers.getAcceptLanguage();
        long contentLength = headers.getContentLength();
        MediaType contentType = headers.getContentType();
        long date = headers.getDate();

        // response.get
        String cookie = headers.getFirst(HttpHeaders.COOKIE);
        log(cookie);
        String sessionid = headers.getFirst("sessionid");
        log(sessionid);
        String accept = headers.getFirst(HttpHeaders.ACCEPT);
        log(accept);
    }
}
