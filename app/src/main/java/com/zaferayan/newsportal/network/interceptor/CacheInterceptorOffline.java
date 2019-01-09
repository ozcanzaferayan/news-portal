package com.zaferayan.newsportal.network.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;
import com.zaferayan.newsportal.config.Constants;
import com.zaferayan.newsportal.network.NetworkHelper;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CacheInterceptorOffline implements Interceptor {

    private final Context context;

    public CacheInterceptorOffline(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetworkHelper.hasNetwork(context)) {
            CacheControl cacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(Constants.NETWORK_MAX_STALE_DAYS, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .cacheControl(cacheControl)
                    .build();
        }

        return chain.proceed(request);
    }
}
