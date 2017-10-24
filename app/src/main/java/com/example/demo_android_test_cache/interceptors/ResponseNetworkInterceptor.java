package com.example.demo_android_test_cache.interceptors;

import com.example.demo_android_test_cache.CacheManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Anson on 2016/5/24.
 */
public class ResponseNetworkInterceptor implements Interceptor {
    private CacheManager cacheManager;

    public ResponseNetworkInterceptor(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Response originalResponse = chain.proceed(chain.request());

        String eTag = originalResponse.headers().get("ETag");
        String lastModified = originalResponse.headers().get("Last-Modified");

        cacheManager.put(originalRequest.method(),originalRequest.url().toString(), CacheManager.Type.E_TAG,eTag);
        cacheManager.put(originalRequest.method(),originalRequest.url().toString(), CacheManager.Type.LAST_MODIFIED,lastModified);

        //System.out.println(">>>>>>>>>>>>>>>>code=" +originalResponse.code());
        //System.out.println(">>>>>>>>>>>>>>>>body=" +originalResponse.body().contentLength());
        return originalResponse;
    }
}