package com.rain.zhihui_community.http;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rain on 2017/6/13 0013.
 * 基础拦截器
 * 用来设置基础headers，这里是通过MAP键值对来构建，将heder加入到Request中。
 */

public class BaseInterceptor implements Interceptor {

    private Map<String, String> headlers;

    public BaseInterceptor(Map<String, String> headlers) {
        this.headlers = headlers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headlers != null && headlers.size() > 0) {
            Set<String> keys = headlers.keySet();
            for (String headerKey :keys
                 ) {
                builder.addHeader(headerKey,headlers.get(headerKey)).build();
                
            }
        }
        return chain.proceed(builder.build());
    }
}
