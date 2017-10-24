package com.example.demo_android_test_cache.interceptors;

import android.content.Context;

import com.anson.andorid_general_library.CommonUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Anson on 2016/5/24.
 */
public class NetworkCheckerApplicationInterceptor implements Interceptor {
    private Context context;

    public NetworkCheckerApplicationInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (CommonUtils.checkNetwork(context) == false) {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            Request compressedRequest = originalRequest.newBuilder()
                    .header("Cache-Control", "public,  max-stale=" + maxStale)
                    .build();
            return chain.proceed(compressedRequest);
        }
        return chain.proceed(originalRequest);
    }
}
