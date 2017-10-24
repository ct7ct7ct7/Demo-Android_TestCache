package com.example.demo_android_test_cache.interceptors;

import com.example.demo_android_test_cache.CacheManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Anson on 2016/5/24.
 */
public class RequestNetworkInterceptor implements Interceptor {
    private CacheManager cacheManager;

    public RequestNetworkInterceptor(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String eTag = cacheManager.get(originalRequest.method(),originalRequest.url().toString(), CacheManager.Type.E_TAG);
        String lastModified = cacheManager.get(originalRequest.method(),originalRequest.url().toString(), CacheManager.Type.LAST_MODIFIED);

        Request compressedRequest = originalRequest.newBuilder()
                .header("If-None-Match", eTag)
                .header("If-Modified-Since", lastModified)
                .build();
        return chain.proceed(compressedRequest);
    }
}
