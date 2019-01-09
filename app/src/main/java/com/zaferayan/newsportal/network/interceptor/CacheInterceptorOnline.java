package com.zaferayan.newsportal.network.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;
import com.zaferayan.newsportal.config.Constants;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CacheInterceptorOnline implements Interceptor {

    private final Context context;

    public CacheInterceptorOnline(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(Constants.NETWORK_MAX_AGE_MINUTES, TimeUnit.MINUTES)
                .build();

        return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .removeHeader("Pragma")
                .build();
    }
}
